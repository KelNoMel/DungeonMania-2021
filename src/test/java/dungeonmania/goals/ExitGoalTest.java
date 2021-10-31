package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class ExitGoalTest {
	@Test
	public void exitGoalTest() {
		DungeonManiaController mania = new DungeonManiaController();
		DungeonResponse response;
		
		response = mania.newGame("exit-basic","Peaceful");
		
		assertFalse(ResponseHelp.goalComplete(response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.goalComplete(response));
	}
}
