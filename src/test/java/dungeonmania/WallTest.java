package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class WallTest {
	@Test
    public void testWallBlock() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("maze","Peaceful");
        
        DungeonResponse moveResponse = mania.tick(null, Direction.LEFT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 1), false), moveResponse));
        
        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 1), false), moveResponse));
        
        moveResponse = mania.tick(null, Direction.UP);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 1), false), moveResponse));
        
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        moveResponse = mania.tick(null, Direction.DOWN);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 3), false), moveResponse));
    }
}
