package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class TreasureGoalTest {

    @Test
    public void treasureGoalTest() {
    	DungeonManiaController mania = new DungeonManiaController();
        
    	DungeonResponse response;
    	response = mania.newGame("treasure-pickup","peaceful");
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
    	response = mania.tick(null, Direction.RIGHT);
    	
    	assertFalse(ResponseHelp.goalComplete(response));
    	
        response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.goalComplete(response));
    }
    
}
