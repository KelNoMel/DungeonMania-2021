package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class DefaultGoalTest {
	@Test
    public void treasureGoalTest() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	response = mania.newGame("default-goal","Peaceful");
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
    	response = mania.tick(null, Direction.RIGHT);
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
        response = mania.tick(null, Direction.DOWN);
        
        assertFalse(ResponseHelp.goalComplete(response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertFalse(ResponseHelp.goalComplete(response));
    }
}
