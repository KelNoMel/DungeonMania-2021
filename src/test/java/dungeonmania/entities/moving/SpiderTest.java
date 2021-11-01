package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTest {
	@Test
	public void testSpider() {
		DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse res = mania.newGame("spider","Hard");
		assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 2)));
		mania.tick(null, Direction.DOWN);
		res = mania.tick(null, Direction.UP);
		// spider should is likely dead
		assertTrue(ResponseHelp.getEntityOfType(res, "spider") == null);
	}
}
