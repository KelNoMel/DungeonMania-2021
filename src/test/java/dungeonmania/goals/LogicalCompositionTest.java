package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class LogicalCompositionTest {
	@Test
    public void treasureGoalTest() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	response = mania.newGame("logical-composition1","peaceful");
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
    	response = mania.tick(null, Direction.RIGHT);
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
        response = mania.tick(null, Direction.DOWN);
        
        assertTrue(ResponseHelp.goalComplete(response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.goalComplete(response));
    }
	
	@Test
    public void treasureGoalTest2() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	response = mania.newGame("logical-composition2","peaceful");
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
    	response = mania.tick(null, Direction.RIGHT);
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
        response = mania.tick(null, Direction.DOWN);
        
        assertFalse(ResponseHelp.goalComplete(response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.goalComplete(response));
    }
}
