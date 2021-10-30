package dungeonmania;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
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
import dungeonmania.entities.collectables.*;
import dungeonmania.entities.collectables.rare.*;
import dungeonmania.entities.buildable.*;

/**
 * Dungeon class describes all aspects of a DungeonMania game
 * @author Matthew Johnstone
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
    
    private EntityList entities = new EntityList();
    private List<AnimationQueue> animations = new ArrayList<AnimationQueue>();  

    private List<String> buildables = new ArrayList<String>();
    
    private String goals;
    private GoalCondition goalCondition;
	private List<Goal> goalsList = new ArrayList<Goal>();
       
    // TODO: fill in empty attribute fields with proper code
    public Dungeon(String dungeonName, String gameMode) throws IllegalArgumentException {
    	this.dungeonName = dungeonName;
    	this.gameMode = GameMode.getGameMode(gameMode);
    	
    	// Load file
    	JSONObject newDungeonData = loadDungeonJSON(dungeonName);
    	
    	// Add the entities
        loadEntities(newDungeonData);        
        
		// Adds goals and sets goal condition
		loadGoals(newDungeonData);
    	
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
    	if (!isMercSpawner()) {
    		createEntity(createMercSpawner());
    	}
    }
    
    private JSONObject createMercSpawner() {
    	JSONObject newMercSpawner = new JSONObject();
    	newMercSpawner.put("x", getPlayer().getPosition().getX());
    	newMercSpawner.put("y", getPlayer().getPosition().getY());
    	newMercSpawner.put("type", "mercenary_spawner");
    	return newMercSpawner;
    }
    
    private boolean isMercSpawner() {
    	for (Entity e : entities) {
    		if (e instanceof MercenarySpawner) {
    			return true;
    		}
    	}
    	return false;
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

	private void loadGoals(JSONObject dungeonData) {
		JSONObject goalData = dungeonData.getJSONObject("goal-condition");

		//JSONObject goalCondition = goalData.getJSONObject("goal");
		switch (goalData.getString("goal")) {
			case "AND":
				goalCondition = GoalCondition.AND;
				break;
			case "OR":
				goalCondition = GoalCondition.OR;
				break;
			default:
				goalsList.add(createGoal(goalData.getString("goal")));
				return;
		}

		// casewhere goalCondition = AND, OR, goaloptions
		JSONArray subGoals = goalData.getJSONArray("subgoals");
		
		int numGoals = subGoals.length();
    	for (int i = 0; i < numGoals; i++) {
    		JSONObject goal = subGoals.getJSONObject(i);
    		goalsList.add(createGoal(goal.getString("goal")));
    	}

	}

	public Goal createGoal(String goalType) {
		switch (goalType) {
			case "exit":
				return new ExitGoal(this);
			case "treasure":
				return new TreasureGoal(this);
			case "boulder":
				return new BoulderGoal(this);
			case "enemies":
				return new EnemiesGoal(this);
			default: 
				System.out.println(goalType + " goal has not been implemented yet.");
				return null;
		}
	}
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Dungeon Saving                              ///
	////////////////////////////////////////////////////////////////////////////////
    
    public void saveGame(File saveFile) {
		try {
			saveFile.createNewFile();
		} catch (IOException e) {
			System.out.println("File save error");
		}
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
    
    // TODO
    /**
     * Checks if the goal has been reached to complete the game
     * @return
     */
    private boolean checkGoalState() {
		switch (goalCondition) {
			case AND:
				for (Goal goal : goalsList) {
					if (goal.checkGoal() == false) return false;
				}
				return true;
			case OR:
				for (Goal goal : goalsList) {
					if (goal.checkGoal() == true) return true;
				}
				return false;
			default:
				return goalsList.get(0).checkGoal();
		}
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

	public Entity getPlayer() {
		return player;
	}
    
    public Entity getEntityFromId(String id) {
    	for (Entity e : entities) {
    		if (e.getId().equals(id)) {
    			return e;
    		}
    	}
    	return null;
    }
    
	// TODO: add goals, buildables, animations
    /**
     * Create a DungeonResponse for the current Dungeon
     * @return DungeonResponse describing the currennt state of the game
     */
    public DungeonResponse response() {
        //return new DungeonResponse(dungeonId, dungeonName, entityResponse(), 
        //    itemResponse(), buildables, goals, animations);
        return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
        itemResponse(), new ArrayList<>(), "");
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
				return new Bow(this, pos.asLayer(itemLayer));
			case "shield":
				return new Shield(this, pos.asLayer(itemLayer));
			
			// Non spec-defined
			case "mercenary_spawner":
				if (numMercenarySpawners < 1) {
					return new MercenarySpawner(this, pos);					
				}
				return null;
			// Type is not correct or has not been implemented
			default:
				System.out.println(ent.getString("type") + " has not been implemented");
				return null;
		}
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


