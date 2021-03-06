package dungeonmania.battles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class BattlePeacefulModeTest {
    @Test
    public void testSimpleBattle() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("health-potion-pickup","peaceful");
        
        for (int i = 0; i < 20; i++) {
            mania.tick(null, Direction.NONE);
        }
          
        DungeonResponse d = mania.tick(null, Direction.NONE);
        
        assertNull(ResponseHelp.getEntityOfType(d, "mercenary"));
        assertNotNull(ResponseHelp.getEntityOfType(d, "player"));
    }
}
