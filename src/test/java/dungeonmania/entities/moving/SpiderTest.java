package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTest {
	@Test
	public void testSpider() {
		DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse res = mania.newGame("spider","hard");
		assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 2)));
		mania.tick(null, Direction.DOWN);
		res = mania.tick(null, Direction.UP);
		// spider should is likely dead
		assertTrue(ResponseHelp.getEntityOfType(res, "spider") == null);
	}

	@Test
	public void testSpiderSpawning() {
		DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse res = mania.newGame("spider","peaceful");
		assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 2)));
		
		mania.tick(null, Direction.DOWN);
		res = mania.tick(null, Direction.UP);
		// spider is likely dead
		assertNull(ResponseHelp.getEntityOfType(res, "spider"));

		for (int i = 0; i < 10; i++) {
			res = mania.tick(null, Direction.UP);
			res = mania.tick(null, Direction.DOWN);
			res = mania.tick(null, Direction.LEFT);
			res = mania.tick(null, Direction.RIGHT);
		}

		// no new spiders have spawned (as on peaceful)
		assertNull(ResponseHelp.getEntityOfType(res, "spider"));

	}
	
	@Test
	public void testSpiderMovement() {
		DungeonManiaController mania = new DungeonManiaController();

		DungeonResponse res = mania.newGame("spider","peaceful");
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "spider", new Position(0, 2), false), res));

        res = mania.tick(null, Direction.UP);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 1)));
				
        res = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(1, 1)));
        
        res = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(1, 2)));
        
        res = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(1, 3)));

        res = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 3)));
        
        res = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(-1, 3)));
        
        res = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(-1, 2)));
        
        res = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(-1, 1)));
        
        res = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.getEntityOfType(res, "spider").getPosition().equals(new Position(0, 1)));


	}
}
