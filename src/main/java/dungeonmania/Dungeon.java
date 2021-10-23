package dungeonmania;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.response.models.AnimationQueue;


/**
 * Dungeon class describes all aspects of a DungeonMania game
 * @author Matthew Johnstone
 * Class Invariants:
 * No two Entities will have the same id
 */
public class Dungeon {
    static private Integer lastId = 0;
    
    private String dungeonId;
    private String dungeonName;
    private GameMode gameMode;
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private List<AnimationQueue> animations = new ArrayList<AnimationQueue>();  

    private Entity player;
    private List<Entity> inventory = new ArrayList<Entity>();
    private List<String> buildables = new ArrayList<String>();
    
    private String goals;
    private GoalCondition goalCondition;

    // TODO: fill in empty attribute fields with proper code
    public Dungeon(String dungeonName, String gameMode) throws IllegalArgumentException {
    	this.dungeonName = dungeonName;
    	this.gameMode = GameMode.getGameMode(gameMode);
    	
    	// Load file
    	JSONObject newDungeonData = loadDungeonJSON(dungeonName);
    	
    	// Add the entities
        loadEntities(newDungeonData);        
        
        // TODO: set goals
        // this.goals = 
    	
    	dungeonId = Dungeon.createId();
    	// this.goalCondition = 
    }
    
    public Dungeon(File loadFile) {
    	String fileData;
    	try {
			fileData = new String(Files.readAllBytes(loadFile.toPath()));
		} catch (IOException e) {
			System.out.println("Unabke to read file " + loadFile + " information");
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
    		
    	} catch (IOException e) {
    		throw new IllegalArgumentException("dungeonName is not a dungeon that exists");
    	}
    }

    private void loadEntities(JSONObject dungeonData) {
    	JSONArray loadEntities = dungeonData.getJSONArray("entities");

    	int numEntities = loadEntities.length();
    	for (int i = 0; i < numEntities; i++) {
    		JSONObject ent = loadEntities.getJSONObject(i);
    		
    		// this.player =  // TODO: properly add the player (here?)
    		Position pos = new Position(ent.getInt("x"), ent.getInt("y"));
            // Entity construction function
    		entities.add(Entity.getEntity(ent));
    	}
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
    public void tick(String itemUsed, Direction movementDirection) {
    	for (Entity e : entities) {
    		e.update();
    	}
    }

    // TODO
    /**
     * Checks if the goal has been reached to complete the game
     * @return
     */
    private boolean checkGoalState() {
        return false;
    }
	
	
	////////////////////////////////////////////////////////////////////////////////
	///                             Dungeon Response                             ///
	////////////////////////////////////////////////////////////////////////////////

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
    	return new ArrayList<ItemResponse>();
    	// Yet to be properly implemented
//        return new ArrayList<ItemResponse>(inventory.stream()
//        .map(e -> new ItemResponse(e.getId(), e.getType()))
//        .collect(Collectors.toList()));
    }
}
