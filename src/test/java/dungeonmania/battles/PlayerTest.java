package dungeonmania.battles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerTest {
    @Test
    public void testSimpleBattle() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.LEFT);
        
        DungeonResponse battleResponse = mania.tick(null, Direction.LEFT);
        assertNull(ResponseHelp.getEntityOfType(battleResponse, "mercenary"));
        assertNotNull(ResponseHelp.getEntityOfType(battleResponse, "player"));
    }
}
