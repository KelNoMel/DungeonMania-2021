package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        mania.newGame("mercenary-bribe","standard");
        
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
        
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
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

    @Test
    public void testMercFrenzy() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("merc-frenzy","Peaceful");
        // player kills the fist mercanary
        mania.tick(null, Direction.NONE);
        // second mercanary is now 2 blocks away so it goes to frenzy mode 
        // to rush to player
        DungeonResponse res = mania.tick(null, Direction.NONE);
        // player kills second mercenary
        assertEquals(null, ResponseHelp.getEntityOfType(res, "mercenary"));
        
    }

}
