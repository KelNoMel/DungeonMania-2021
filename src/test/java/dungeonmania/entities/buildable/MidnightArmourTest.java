package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    public void testCreate() {
        DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("midnight-armour","hard");
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("midnight_armour"));
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertEquals(null, ResponseHelp.getItemOfType(response, "midnight_armour"));
		assertDoesNotThrow(() -> mania.build("midnight_armour"));
        response = mania.tick(null, Direction.NONE);
        assertNotEquals(null, ResponseHelp.getItemOfType(response, "midnight_armour"));
    }

    @Test
    public void testInvalid() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("midnight-armour","hard");
        assertThrows(InvalidActionException.class, () -> mania.build("midnight_armour"));
    }
}
