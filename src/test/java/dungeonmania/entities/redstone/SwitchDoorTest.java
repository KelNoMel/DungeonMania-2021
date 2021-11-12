package dungeonmania.entities.redstone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwitchDoorTest {
	@Test
	public void testSwitchDoor() {
		DungeonManiaController mania = new DungeonManiaController();
        
		DungeonResponse response1;
		DungeonResponse response2;
		
		mania.newGame("switch-door", "peaceful");
        
		// Check door is locked
		mania.tick(null, Direction.UP);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		response1 = mania.tick(null, Direction.RIGHT);
		response2 = mania.tick(null, Direction.DOWN);
		
		assertTrue(ResponseHelp.dungeonEqual(response1, response2));
		
		// Engage door
		mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
		
		// Check door unlocked
		mania.tick(null, Direction.UP);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		response1 = mania.tick(null, Direction.DOWN);
		
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), response1));
	}
}
