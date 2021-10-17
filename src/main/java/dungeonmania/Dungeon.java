package dungeonmania;

import java.util.List;
import java.util.ArrayList;

/**
 * Dungeon class describes all aspects of a dungeonmania game
 * @author Matthew Johnstone
 * Class Invarients:
 * No two Entities will have the same id
 */
public class Dungeon {
    static private Integer lastId;
    
    private String dungeonId;
    private String dungeonName;
    private GameMode gameMode;
    private GoalCondition goalCondition;
    private List<List<String>> map;
    private ArrayList<Entity> entities;
    private Entity player;

    /**
     * Creates a new id by adding 1 to the integer value of the last id created
     * Note: This may generate used ids persistance is added.
     * @return new unique dungeon id
     */
    private static String createId() {
        return String.valueOf(++lastId); 
    }

    public Dungeon(String dungeonName) {
        this.dungeonId = Dungeon.createId();
        this.dungeonName = dungeonName;
        
    }

    /**
     * Advance the game one tick into the future
     */
    public void tick() {

    }

    /**
     * Checks if the goal has been reached to complete the game
     * @return
     */
    private boolean checkGoalState() {
        return false;
    }

}
