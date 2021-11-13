package dungeonmania;

import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.*;
import dungeonmania.entities.*;
import dungeonmania.entities.statics.*;
import dungeonmania.goals.*;
import dungeonmania.entities.spawners.MercenarySpawner;
import dungeonmania.entities.spawners.SpiderSpawner;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.*;

/**
 * Dungeon class describes all aspects of a DungeonMania game
 * Class Invariants:
 * No two Entities will have the same id
 */
public class Dungeon {

	private String dungeonId;
    private String dungeonName;
    private Gamemode gamemode;
    
    private EntityList entities = new EntityList();
    private List<AnimationQueue> animations = new ArrayList<AnimationQueue>();  
    
    private Goal dungeonGoal;
    
	////////////////////////////////////////////////////////////////////////////////
	///                         New Dungeon Construction                         ///
	////////////////////////////////////////////////////////////////////////////////
    
    private static String createId() {
        return UUID.randomUUID().toString();
    }
    
    public Dungeon(String dungeonName, String gamemode) throws JSONException {
        this.dungeonName = dungeonName;
    	this.gamemode = Gamemode.getGamemode(gamemode);
    	
    	// Load file
    	JSONObject dungeonJSON = readDungeonJSON(dungeonName);
    	
    	// Add the entities
    	Portal.clearPortalLinks();
        EntityFactory.loadEntities(dungeonJSON.getJSONArray("entities"), this, entities);        
        loadOther();
        
		// Adds goals and sets goal condition
		dungeonGoal = loadGoalFromFile(dungeonJSON);
    	
    	dungeonId = Dungeon.createId();
    }
    
    private JSONObject readDungeonJSON(String dungeonName) throws IllegalArgumentException {
    	try {
    		return new JSONObject(
    				FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json")
    				);
    		
    	} catch (Exception e) {
    		throw new IllegalArgumentException("dungeonName is not a dungeon that exists");
    	}
    }
    
    private Goal loadGoalFromFile(JSONObject dungeonJSON) {
    	JSONObject goalInfo;
    	try {
    		goalInfo = dungeonJSON.getJSONObject("goal-condition");
    		return loadGoals(goalInfo);
    	} catch (JSONException e) {
    		return new DefaultGoal(this);
    	}
    }
    
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Dungeon Loading                             ///
	////////////////////////////////////////////////////////////////////////////////
    
    public Dungeon(File loadFile) {
    	JSONObject fileData;
    	try {
			fileData = new JSONObject(new String(Files.readAllBytes(loadFile.toPath())));
		} catch (IOException e) {
			System.out.println("Unable to read/load file " + loadFile.toPath() + " information");
			throw new IllegalArgumentException("Unable to create the dungeon from the selected file");
		}
    	
    	System.out.println(fileData.toString(2));
    	
    	dungeonName = fileData.getString("dungeon-name");
    	gamemode = Gamemode.getGamemode(fileData.getString("gamemode"));
    	Portal.clearPortalLinks();
    	EntityFactory.loadEntities(fileData.getJSONArray("entities"), this, entities);
    	loadOther();
    	dungeonGoal = loadGoalFromFile(fileData);
    }

	////////////////////////////////////////////////////////////////////////////////
	///                              JSON Extraction                             ///
	////////////////////////////////////////////////////////////////////////////////
    
    private void loadOther() {
    	// If no merc spawner, load in
    	if (entities.numEntitiesOfType(MercenarySpawner.class) == 0) {
    		Position p = getPlayer().getPosition();
    		EntityFactory.constructEntity(newEntityJSON(p.getX(), p.getY(), "mercenary_spawner"), this);
    	}
    	
    	if (entities.numEntitiesOfType(SpiderSpawner.class) == 0) {
    		EntityFactory.constructEntity(newEntityJSON(0, 0, "spider_spawner"), this);
    	}
    	// Should be singleton??
    	if (entities.numEntitiesOfType(BattleResolver.class) == 0) {
    		EntityFactory.constructEntity(newEntityJSON(0, 0, "battle_resolver"), this);
    	}
    }
    
    private Goal loadGoals(JSONObject goalJSON) {
		JSONArray subgoals;
		
		switch(goalJSON.getString("goal")) {
			case "AND":
				subgoals = goalJSON.getJSONArray("subgoals");
				return new AndGoal(this, loadGoals(subgoals.getJSONObject(0)), loadGoals(subgoals.getJSONObject(1)));
			case "OR":
				subgoals = goalJSON.getJSONArray("subgoals");
				return new OrGoal(this, loadGoals(subgoals.getJSONObject(0)), loadGoals(subgoals.getJSONObject(1)));
			case "exit":
				return new ExitGoal(this);
			case "treasure":
				return new TreasureGoal(this);
			case "boulders":
				return new BoulderGoal(this);
			case "enemies":
				return new EnemiesGoal(this);
			case "default":
				return new DefaultGoal(this);
			default:
				throw new IllegalArgumentException("GoalJSON is invalid");
		}
	}
    
    private JSONObject newEntityJSON(int x, int y, String entType) {
    	JSONObject newEntity = new JSONObject();
    	newEntity.put("x", getPlayer().getPosition().getX());
    	newEntity.put("y", getPlayer().getPosition().getY());
    	newEntity.put("type", entType);
    	return newEntity;
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Dungeon Saving                              ///
	////////////////////////////////////////////////////////////////////////////////
    
    public void saveGame(File saveFile) {
		try {
			// Make sure the file exists and is ready to write to
			saveFile.delete();
			saveFile.createNewFile();
		} catch (IOException e) {
			System.out.println("File save error 1");
			return;
		}
		
		// Retrieve all data to save
		JSONObject saveData = new JSONObject();
		saveData.put("entities", entities.toJSON());
		saveData.put("goal-condition", dungeonGoal.toJSON());
		saveData.put("gamemode", gamemode.asString());
		saveData.put("dungeon-name", dungeonName);
		
		try {
			// Write the file
			FileWriter writer = new FileWriter(saveFile);
			writer.write(saveData.toString(4));
			writer.close();
		} catch (IOException e) {
			System.out.println("File save error 2");
			return;
		}
		System.out.println("Saved game at location " + saveFile.getPath());
	}
    
	////////////////////////////////////////////////////////////////////////////////
	///                           Dungeon State Change                           ///
	////////////////////////////////////////////////////////////////////////////////

    /**
     * Advance the game one tick into the future
     * @param movementDirection 
     * @param itemUsed 
     */
    public void tick(InputState inputState) {
    	animations.clear();
    	processInput(inputState);
    	updateGame();
    }
    
    private void processInput(InputState inputState) {
    	entities.processInput(inputState);
    }
    
    private void updateGame() {
    	entities.updateEntities();
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                             Dungeon Response                             ///
	////////////////////////////////////////////////////////////////////////////////

	// TODO
	/**
	 * Get the types of buildables possible
	 * @return list of strings for each type of buildable
	 */
	public List<String> buildableResponse() {
		return Buildable.response(getPlayer());
	}
    
	// TODO: add goals, animations
    /**
     * Create a DungeonResponse for the current Dungeon
     * @return DungeonResponse describing the currennt state of the game
     */
    public DungeonResponse response() {
        Player p = getPlayer();
    	if (p == null) {
    		return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
    				new ArrayList<ItemResponse>(), new ArrayList<String>(), dungeonGoal.response());
    	}
        return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
        itemResponse(), buildableResponse(), dungeonGoal.response(), animations);
    }
    
    /**
     * Create a list of EntityResponse for each entity on the map
     * @return list of EntityResponse for all entities
     */
    private List<EntityResponse> entityResponse() {
        return new ArrayList<EntityResponse>(entities.stream()
            .map(e -> e.response()).collect(Collectors.toList()));
    }
	
	/**
     * Create a list of ItemRespones for each item in the player's inventory
     * @return list of all ItemResponses for the inventory
     */
    private List<ItemResponse> itemResponse() {
        return getPlayer().getInventoryResponse();
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                            Entity Construction                           ///
	////////////////////////////////////////////////////////////////////////////////
   

	public void build(String buildable) {
		getPlayer().build(buildable);
	}

	////////////////////////////////////////////////////////////////////////////////
	///                              Helper Methods                              ///
	////////////////////////////////////////////////////////////////////////////////
	
	public void queueAnimation(AnimationQueue q) {
		animations.add(q);
	}
	
	public EntityList getEntities() {
    	return entities;
    }

    public List<Entity> getEntitiesInRadius(Position origin, Double radius) {
    	List<Entity> radEnts = new ArrayList<>();
    	for (Entity e : entities) {
    		if (Position.withinRange(origin, e.getPosition(), radius)) {
    			radEnts.add(e);
    		}
    	}
    	return radEnts;
    }
    
    public List<Entity> getEntitiesAtPosition(Position checkPosition) {
    	List<Entity> posEnts = new ArrayList<>();
    	for (Entity e : entities) {
    		if (e.getPosition().equals(checkPosition)) {
    			posEnts.add(e);
    		}
    	}
    	return posEnts;
    }
    
    public Entity getEntityFromId(String id) {
    	for (Entity e : entities) {
    		if (e.getId().equals(id)) {
    			return e;
    		}
    	}
    	return null;
    }
	
	/**
	 * Get the player
	 * Assumes only one player always exists and it is stored as the first 
	 * entity in entities
	 * @return player
	 */
	public Player getPlayer() {
		if (entities.get(0) instanceof Player) {
			return (Player)entities.get(0);			
		}
		return null;
	}
	
	public Gamemode getGamemode() {
		return gamemode;
	}
	
	/**
	 * Check if the player is in a position
	 * @param pos
	 * @return true if player is at the (x,y) location. False otherwise.
	 */
	public boolean isPlayerHere(Position pos) {
		return getPlayer().getPosition().equals(pos);
	}

	public void transferToInventory(Entity e) {
		e.toggleDisplay(false);
		entities.transferEntity(getPlayer().getInventory(), e);
	}

	public void transferToMap(Entity e) {
		e.toggleDisplay(true);
		getPlayer().getInventory().transferEntity(entities, e);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public class Bounds {
		private Position minBounds;
		private Position maxBounds;
		
		public Bounds(Position minBounds, Position maxBounds) {
			this.minBounds = minBounds;
			this.maxBounds = maxBounds;
		}
		
		public Position getMinBounds() {
			return minBounds;
		}
		
		public Position getMaxBounds() {
			return maxBounds;
		}
	}
	
	public Bounds getBounds() {
		Position startPos = entities.get(0).getPosition();
		int minX = startPos.getX();
		int maxX = startPos.getX();
		int minY = startPos.getY();
		int maxY = startPos.getY();
		
		for (Entity e : entities) {
			Position entPos = e.getPosition();
			int checkX = entPos.getX();
			int checkY = entPos.getY();

			
			if (checkX < minX) {
				minX = checkX;
			} else if (checkX > maxX) {
				maxX = checkX;
			}
			
			if (checkY < minY) {
				minY = checkY;
			} else if (checkY > maxY) {
				maxY = checkY;
			}
		}
		
		return new Bounds(new Position(minX, minY), new Position(maxX, maxY));
	}
}


