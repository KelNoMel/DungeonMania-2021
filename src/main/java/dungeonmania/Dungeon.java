package dungeonmania;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
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
 * Dungeon class describes all aspects of a dungeonmania game
 * @author Matthew Johnstone
 * Class Invarients:
 * No two Entities will have the same id
 */
public class Dungeon {
    static private Integer lastId = 0;
    
    private String dungeonId;
    private String dungeonName;
    private GameMode gameMode;
    private GoalCondition goalCondition;
    private ArrayList<Entity> entities;
    private Entity player;
    
    private List<Entity> inventory;
    private List<String> buildables;
    private String goals;
    private List<AnimationQueue> animations;  

    /**
     * Creates a new id by adding 1 to the integer value of the last id created
     * Note: This may generate used ids if persistance is added. Use UUID's in 
     *       that case.
     * @return new unique dungeon id
     */
    private static String createId() {
        return String.valueOf(++lastId); 
    }

    // TODO: fill in empty attribute fields with proper code
    /**
     * Construct a Dungeon object
     * Initialise attributes to match the map chosen
     * @param dungeonName
     * @throws IllegalArgumentException if file (dungeonName) or path does not exist
     */
    public Dungeon(String dungeonName, String gameMode)  throws IllegalArgumentException {
    	this.dungeonId = Dungeon.createId();
    	this.dungeonName = dungeonName;
    	this.entities = new ArrayList<Entity>();
    	try {
    		this.loadDungeon();
    	} catch (IOException e) {
    		throw new IllegalArgumentException("dungeonName is not a dungeon that exists");
    	}
    	
    	// this.gameMode = 
    	// this.goalCondition = 
    	this.inventory = new ArrayList<Entity>();
    	this.buildables = new ArrayList<String>();
    	this.animations = null;
    }
    
    /**
     * Add the entities, goals, player to the Dungeon 
     * from the dungeonName specified
     * @throws IOException
     */
    private void loadDungeon() throws IOException {
        // throw exception if the dungeonName can not be found
        String file = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        JSONObject json = new JSONObject(file);

        // TODO check the map works

        // add the entities
        JSONArray jsonEntities = json.getJSONArray("entities");

        for (Object entity : jsonEntities) {
            if (entity instanceof JSONObject) {
                // this.player =  // TODO: properly add the player

                JSONObject ent = (JSONObject)entity;
                Position pos = new Position(ent.getInt("x"), ent.getInt("y"));
                this.entities.add(new Entity(this, ent.getString("type"), pos));
            }
        }

        // TODO: set goals
        // this.goals = 
    }

    public String getName() {
        return dungeonName;
    }

    /**
     * Create a list of ItemRespones for each item in the player's inventory
     * @return list of all ItemResponses for the inventory
     */
    private List<ItemResponse> itemResponse() {
        return new ArrayList<ItemResponse>(inventory.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
    }

    /**
     * Create a list of EntityResponse for each entity on the map
     * @return list of EntityResponse for all entities
     */
    private List<EntityResponse> entityResponse() {
        return new ArrayList<EntityResponse>(entities.stream()
            .map(e -> e.response()).collect(Collectors.toList()));
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
     * Advance the game one tick into the future
     * @param movementDirection 
     * @param itemUsed 
     */
    public void tick(String itemUsed, Direction movementDirection) {
    	for (Entity e : entities) {
    		e.
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

}
