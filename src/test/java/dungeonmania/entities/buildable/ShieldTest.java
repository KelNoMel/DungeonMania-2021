package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Direction;

public class ShieldTest {
	@Test
	public void testShield() {
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
		assertDoesNotThrow(() -> mania.build("shield"));
	}

	@Test
	public void testShieldInvalid() {
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
		//assertThrows(Ill, () -> mania.build("shield"));
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
		assertDoesNotThrow(() -> mania.build("shield"));
	}

	
}
