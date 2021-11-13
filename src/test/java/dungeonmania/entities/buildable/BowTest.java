package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;

public class BowTest {
	@Test
	public void createBow() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced-2","peaceful");
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

	@Test
	public void createBowLowMats() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced-2","peaceful");
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
		assertThrows(InvalidActionException.class, () -> mania.build("bow"));
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
