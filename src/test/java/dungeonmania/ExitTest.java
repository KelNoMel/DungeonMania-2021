package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

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
        mania.newGame("maze", "Peaceful");

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

        // TODO : 
        // Check that the player moved 
        //assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 1), false), movedResponse));

        // Check that player is at exit
        //assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(18, 16), false), movedResponse));
        //assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "exit", new Position(18, 16), false), movedResponse));

        //Exit exit = ResponseHelp.getEntityOfType(movedResponse, "exit");
        assertTrue(); //.checkGoal();
        // check that Exit.playerAtExit() returns true // TODO: check via goal condition being met. 

    }
    
}
