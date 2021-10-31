package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;

public class BuildableTest {
    @Test
	public void testShieldInvalid() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("advanced-2","Peaceful");

		// assertThrows(IllegalArgumentException, () -> mania.build("semester plan"));
	}
}
