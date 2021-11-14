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
	
    @Test
    public void testBasicMerc() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("mercenary-bribe","standard");
        
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
        String mercID = ResponseHelp.getEntityOfType(d, "mercenary").getId();
        
        assertDoesNotThrow(()->mania.interact(mercID));
        
        for (int i = 0; i < 19; i++) {
        	mania.tick(null, Direction.DOWN);        	
        }
        
        // Spawn!
        d = mania.tick(null, Direction.DOWN);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "mercenary", new Position(0, 2), true), d));
    }

    @Test
    public void testMercFrenzy() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("merc-frenzy","peaceful");
        // player kills the fist mercenary
        mania.tick(null, Direction.NONE);
        // second mercanary is now 2 blocks away so it goes to frenzy mode 
        // to rush to player
        DungeonResponse res = mania.tick(null, Direction.NONE);
        // player kills second mercenary
        assertEquals(null, ResponseHelp.getEntityOfType(res, "mercenary"));
        
    }

}
