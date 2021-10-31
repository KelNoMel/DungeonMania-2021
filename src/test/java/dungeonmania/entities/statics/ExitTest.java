package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.entities.statics.Exit;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ExitTest {

    @Test
    public void testPlayerAtExit() {
        DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse newDungeon = mania.newGame("maze", "Peaceful");
        
        assertFalse(ResponseHelp.goalComplete(newDungeon));

        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);

        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);

        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.RIGHT);

        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
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
        mania.tick(null, Direction.DOWN);

        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);

        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);

        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);

        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);

        DungeonResponse movedResponse = mania.tick(null, Direction.DOWN);

        // Check that player is at exit
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(18, 16), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "exit", new Position(18, 16), false), movedResponse));

        assertTrue(ResponseHelp.goalComplete(movedResponse));

    }
    
}
