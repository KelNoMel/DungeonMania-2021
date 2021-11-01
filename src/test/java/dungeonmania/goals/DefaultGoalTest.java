package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class DefaultGoalTest {
	@Test
    public void defaultGoalTest() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	response = mania.newGame("default-goal2","Peaceful");
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
    	response = mania.tick(null, Direction.RIGHT);
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
        response = mania.tick(null, Direction.DOWN);
        
        assertFalse(ResponseHelp.goalComplete(response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertFalse(ResponseHelp.goalComplete(response));
    }
	
	@Test
    public void defaultGoalTestLoad() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	
    	response = mania.newGame("default-goal","Peaceful");
    	mania.saveGame("SaveGame");
    	DungeonResponse newResponse = mania.loadGame("SaveGame");
    	
    	ResponseHelp.dungeonEqual(response, newResponse);
	}
}
