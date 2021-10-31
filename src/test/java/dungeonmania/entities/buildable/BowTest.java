package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;

public class BowTest {
	@Test
	public void createBow() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced-2","Peaceful");
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> mania.build("bow"));
	}
}
