package dungeonmania.entities.redstone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LightbulbTest {
	@Test
	public void testSwitchDoor() {
		DungeonManiaController mania = new DungeonManiaController();
        
		DungeonResponse response;
		
		response = mania.newGame("lightbulb", "peaceful");
        
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "light_bulb_off", new Position(2, 0), false), response));
				
		response = mania.tick(null, Direction.RIGHT);

		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "light_bulb_on", new Position(2, 0), false), response));
	}
}
