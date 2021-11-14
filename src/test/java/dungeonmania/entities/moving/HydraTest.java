package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HydraTest {
	
    @Test
    public void testBasicHydra() {
        DungeonManiaController mania = new DungeonManiaController();
		DungeonResponse d = mania.newGame("hydra-anduril","standard");
        
        for (int i = 0; i < 20; i++) {
            if (ResponseHelp.getEntityOfType(d, "player") != null) {
                d = mania.tick(null, Direction.UP);
            } else {
                break;
            }
        }

		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 1), false), d));
    }

    @Test
    public void testHydraSlain() {
        
        // Hydra moves randomly, so just to make sure they fight and player wins
        for (int i = 0; i < 5; i++) {
            DungeonManiaController mania = new DungeonManiaController();
            DungeonResponse d = mania.newGame("hydra-anduril","standard");
            
            mania.tick(null, Direction.LEFT);
            d = mania.tick(null, Direction.RIGHT);
            // Make sure they fight
            for (int j = 0; j < 20; j++) {
                d = mania.tick(null, Direction.DOWN);
            }
            // If player didn't have Anduril, Hydra should live
            assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 1), false), d));
            assertEquals(null, ResponseHelp.getEntityOfType(d, "hydra"));
        }
    }

}
