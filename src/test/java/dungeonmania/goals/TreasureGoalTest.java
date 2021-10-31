package dungeonmania.goals;

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
        mania.newGame("treasure-pickup","Peaceful");
        mania.tick(null, Direction.RIGHT);
        
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.goalComplete(response));
    }
    
}
