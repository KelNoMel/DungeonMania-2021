package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
	
    // Succeed at moving a boulder with nothing behind it
    @Test
    public void testMoveBoulder() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("boulders","Peaceful");
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player and boulder moved
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(3, 2), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(4, 2), false), movedResponse));
    }
}
