package dungeonmania.entities.buildable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SceptreTest {
    @Test
	public void testSceptreBuild() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("sceptre-basic","peaceful");
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertEquals(null, ResponseHelp.getItemOfType(response, "sceptre"));
		assertDoesNotThrow(() -> mania.build("sceptre"));
        response = mania.tick(null, Direction.NONE);
        assertNotEquals(null, ResponseHelp.getItemOfType(response, "sceptre"));
	}

    @Test
	public void testSceptreBuildAlternate() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("sceptre-alt","peaceful");
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
        mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertEquals(null, ResponseHelp.getItemOfType(response, "sceptre"));
		assertDoesNotThrow(() -> mania.build("sceptre"));
        response = mania.tick(null, Direction.NONE);
        assertNotEquals(null, ResponseHelp.getItemOfType(response, "sceptre"));
	}

    @Test
    public void testSceptreInvalidBuild() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("sceptre-basic","peaceful");
        assertThrows(InvalidActionException.class, () -> mania.build("sceptre"));
    }

    @Test
	public void testSceptreBribe() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("sceptre-basic","standard");
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> mania.build("sceptre"));
		mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.RIGHT);
        DungeonResponse res = mania.tick(null, Direction.RIGHT);
        // control mercenary
        String mercID = ResponseHelp.getEntityOfType(res, "mercenary").getId();
        assertDoesNotThrow(()->mania.interact(mercID));
        // stand for 9 more ticks not fighting
        for (int i = 0; i < 9; i++) {
            res = mania.tick(null, Direction.NONE);
        }
        // player and merc are still alive
        assertNotEquals(null, ResponseHelp.getEntityOfType(res, "mercenary"));
        assertNotEquals(null, ResponseHelp.getEntityOfType(res, "player"));
        // player kills mercenary as mind control runs out
        res = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(res, "mercenary"));
	}
    
    @Test
    public void testSceptreOutOfRange() {
        DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("sceptre-basic","standard");
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        DungeonResponse res = mania.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> mania.build("sceptre"));
        String mercID = ResponseHelp.getEntityOfType(res, "mercenary").getId();
        assertThrows(InvalidActionException.class, () -> mania.interact(mercID));
    }
}
