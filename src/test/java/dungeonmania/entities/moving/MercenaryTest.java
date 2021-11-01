package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTest {
	
    // Succeed at moving a boulder with nothing behind it
    @Test
    public void testBasicMerc() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("mercenary-bribe","Standard");
        
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
        String mercID = ResponseHelp.getEntityOfType(d, "mercenary").getId();
        
        assertDoesNotThrow(()->mania.interact(mercID));
        
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        // Spawn!
        d = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "mercenary", new Position(-1, 0), true), d));
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.DOWN);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "mercenary", new Position(-1, 0), true), d));
    }

}
