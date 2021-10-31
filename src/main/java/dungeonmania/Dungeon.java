package dungeonmania;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.*;
import dungeonmania.entities.*;
import dungeonmania.entities.statics.*;
import dungeonmania.goals.*;
import dungeonmania.entities.moving.*;
import dungeonmania.entities.spawners.MercenarySpawner;
import dungeonmania.entities.spawners.SpiderSpawner;
import dungeonmania.entities.collectables.*;
import dungeonmania.entities.collectables.rare.*;
import dungeonmania.entities.buildable.*;

/**
 * Dungeon class describes all aspects of a DungeonMania game
 * Class Invariants:
 * No two Entities will have the same id
 */
public class Dungeon {
    static private Integer lastId = 0;
    static private final int maxMercenarySpawners = 1;
    
    private int numMercenarySpawners = 0;
    private String dungeonId;
    private String dungeonName;
    private GameMode gameMode;
    
    private EntityList entities = new EntityList("entities");
    private List<AnimationQueue> animations = new ArrayList<AnimationQueue>();  

    private List<String> buildables = new ArrayList<String>();
    
    private Goal dungeonGoal;
    
    // TODO: fill in empty attribute fields with proper code
    public Dungeon(String dungeonName, String gameMode) throws IllegalArgumentException {
    	this.dungeonName = dungeonName;
    	this.gameMode = GameMode.getGameMode(gameMode);
    	
    	// Load file
    	JSONObject dungeonJSON = loadDungeonJSON(dungeonName);
    	
    	// Add the entities
        loadEntities(dungeonJSON);        
        
		// Adds goals and sets goal condition
		dungeonGoal = loadGoals(dungeonJSON.getJSONObject("goal-condition"));
    	
    	dungeonId = Dungeon.createId();
    }
    
    public Dungeon(File loadFile) {
    	String fileData;
    	try {
			fileData = new String(Files.readAllBytes(loadFile.toPath()));
		} catch (IOException e) {
			System.out.println("Unable to read file " + loadFile + " information");
		}
    	System.out.println("Loading saved files is not yet implemented!");
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                       Dungeon Loading/Construction                       ///
	////////////////////////////////////////////////////////////////////////////////
    
    private JSONObject loadDungeonJSON(String dungeonName) throws IllegalArgumentException {
    	try {
    		return new JSONObject(
    				FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json")
    				);
    		
    	} catch (Exception e) {
    		throw new IllegalArgumentException("dungeonName is not a dungeon that exists");
    	}
    }

    private void loadEntities(JSONObject dungeonData) {
    	JSONArray loadEntities = dungeonData.getJSONArray("entities");

    	int numEntities = loadEntities.length();
    	for (int i = 0; i < numEntities; i++) {
    		JSONObject ent = loadEntities.getJSONObject(i);
    		
    		Position pos = new Position(ent.getInt("x"), ent.getInt("y"));
            // Entity construction function
    		createEntity(ent);
    	}
    	
    	// If no merc spawner, load in
    	if (numEntitiesOfType(MercenarySpawner.class) == 0) {
    		Position p = getPlayer().getPosition();
    		createEntity(newEntityJSON(p.getX(), p.getY(), "mercenary_spawner"));
    	}
    	
    	if (numEntitiesOfType(SpiderSpawner.class) == 0) {
    		createEntity(newEntityJSON(0, 0, "spider_spawner"));
    	}
    }
    
    private JSONObject newEntityJSON(int x, int y, String entType) {
    	JSONObject newMercSpawner = new JSONObject();
    	newMercSpawner.put("x", getPlayer().getPosition().getX());
    	newMercSpawner.put("y", getPlayer().getPosition().getY());
    	newMercSpawner.put("type", entType);
    	return newMercSpawner;
    }

    public int numEntitiesOfType(Class<?> classType) {
    	int numOfType = 0;
    	for (Entity e : entities) {
    		if (classType.isInstance(e)) {
    			numOfType++;
    		}
    	}
    	return numOfType;
    }
    
	public void addEntity(Entity e) {
		entities.add(e);
	}

    /**
     * Creates a new id by adding 1 to the integer value of the last id created
     * Note: This may generate used ids if persistence is added. Use UUID's in 
     *       that case.
     * @return new unique dungeon id
     */
    private static String createId() {
        return String.valueOf(++lastId); 
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
			default:
				throw new IllegalArgumentException("GoalJSON is invalid");
		}
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
		
		// Get save data
		JSONObject saveData = new JSONObject();
		
		// Get all entitity data
		JSONArray entitySaveData = new JSONArray();
		entitySaveData.putAll(entities.toJSON());
		entitySaveData.putAll(getPlayer().getInventory().toJSON());
		
		// Get all goal data
		JSONObject goalSaveData = dungeonGoal.toJSON();
		
		saveData.put("entities", entitySaveData);
		saveData.put("goal-condition", goalSaveData);
		
		try {
			// Write the file
			FileWriter writer = new FileWriter(saveFile);
			writer.write(saveData.toString(4));
			writer.close();
		} catch (IOException e) {
			System.out.println("File save error 2");
			return;
		}
		

		
		System.out.println(entitySaveData);
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

    public List<Entity> getEntities() {
    	return entities;
    }

    public List<Entity> getEntitiesInRadius(Position origin, int radius) {
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
        //return new DungeonResponse(dungeonId, dungeonName, entityResponse(), 
        //    itemResponse(), buildables, goals, animations);
        return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
        itemResponse(), buildableResponse(), dungeonGoal.response());
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
    
    // TODO: Research better way to do this
    
    /**
     * Used to construct specific entities given their JSON representation
     * @param ent
     * @return
     */
	public Entity createEntity(JSONObject ent) {
		Position pos = new Position(ent.getInt("x"), ent.getInt("y"));
		
		int topLayer = 3;
		int movingLayer = 2;
		int itemLayer = 1;
		int bottomLayer = 0;
		
		switch (ent.getString("type")) {
			case "player":
				Entity player = new Player(this, pos.asLayer(topLayer));
				// Make sure player is updated first
				Collections.swap(entities, 0, entities.indexOf(player));
				return player;
			// Statics
			case "wall":
				return new Wall(this, pos.asLayer(bottomLayer));
			case "exit":
				return new Exit(this, pos.asLayer(bottomLayer));
			case "boulder":
				return new Boulder(this, pos.asLayer(bottomLayer));
			case "switch":
				return new FloorSwitch(this, pos.asLayer(bottomLayer));
			case "door":
				return new Door(this, pos.asLayer(bottomLayer));
			case "portal":
				return new Portal(this, pos.asLayer(bottomLayer), ent.getString("colour"));
			case "spawner":
				return new ZombieToastSpawner(this, pos.asLayer(bottomLayer));
			
			// Moving
			case "spider":
				return new Spider(this, pos.asLayer(movingLayer));
			case "zombie":
				return new ZombieToast(this, pos.asLayer(movingLayer));
			case "mercenary":
				return new Mercenary(this, pos.asLayer(movingLayer));
				
			// Collectable
			case "treasure":
				return new Treasure(this, pos.asLayer(itemLayer));
			case "key":
				return new Key(this, pos.asLayer(itemLayer));
			case "health_potion":
				return new HealthPotion(this, pos.asLayer(itemLayer));
			case "invincibility_potion":
				return new InvincibilityPotion(this, pos.asLayer(itemLayer));
			case "invisibility_potion":
				return new InvisibilityPotion(this, pos.asLayer(itemLayer));
			case "wood":
				return new Wood(this, pos.asLayer(itemLayer));
			case "arrows":
				return new Arrows(this, pos.asLayer(itemLayer));
			case "bomb":
				return new Bomb(this, pos.asLayer(itemLayer));
			case "sword":
				return new Sword(this, pos.asLayer(itemLayer));
			case "armour":
				return new Armour(this, pos.asLayer(itemLayer));
				
			// Rare Collectable
			case "the_one_ring":
				return new TheOneRing(this, pos.asLayer(itemLayer));
				
			/// Buildable
			case "bow":
				Bow bow = new Bow(this, pos.asLayer(itemLayer));
				transferToInventory(bow);
				return bow;
			case "shield":
				Shield shield = new Shield(this, pos.asLayer(itemLayer));
				transferToInventory(shield);
				return shield;
			
			// Non spec-defined
			case "mercenary_spawner":
				return new MercenarySpawner(this, pos, 10);
			case "spider_spawner":
				// TODO load spawner info from save
				return new SpiderSpawner(this, pos, 5);
			// Type is not correct or has not been implemented
			default:
				System.out.println(ent.getString("type") + " has not been implemented");
				return null;
		}
	}

	public void build(String buildable) {
		getPlayer().build(buildable);
	}

	////////////////////////////////////////////////////////////////////////////////
	///                              Helper Methods                              ///
	////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * Get the player
	 * Assumes only one player always exists and it is stored as the first 
	 * entity in entities
	 * @return player
	 */
	public Player getPlayer() {
		return (Player)entities.get(0);
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
}


