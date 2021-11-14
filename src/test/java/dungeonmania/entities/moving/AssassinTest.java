package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class AssassinTest {
	
    @Test
    public void testBasicAssassin() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("assassin-bribe","standard");
        
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
        d = mania.tick(null, Direction.RIGHT);

        String assID = ResponseHelp.getEntityOfType(d, "assassin").getId();
        
        assertDoesNotThrow(()->mania.interact(assID));
        
        for (int i = 0; i < 30; i++) {
        	mania.tick(null, Direction.NONE);        	
        }
        d = mania.tick(null, Direction.NONE);
        assertNotEquals(null, ResponseHelp.getEntityOfType(d, "player"));
        assertNotEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));
    }

    @Test
    public void testInvalidAssassinBribe() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("assassin-bribe","standard");
        DungeonResponse d = mania.tick(null, Direction.NONE);
        String assID = ResponseHelp.getEntityOfType(d, "assassin").getId();
        
        assertThrows(InvalidActionException.class, ()->mania.interact(assID));
    }
}
