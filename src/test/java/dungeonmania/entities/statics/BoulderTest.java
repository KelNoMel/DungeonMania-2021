package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderTest {
	
	
    // Succeed at moving a boulder with nothing behind it
    @Test
    public void testMoveBoulder() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("boulders","peaceful");
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player and boulder moved
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(3, 2), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(4, 2), false), movedResponse));
    }

    // Fail to move a boulder with another boulder behind it
    @Test
    public void testBoulderChain() {
    	DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("boulders","peaceful");
        
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        DungeonResponse movedResponse = mania.tick(null, Direction.DOWN);

        // Check that the player and boulders didn't move
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(4, 2), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(4, 3), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(4, 4), false), movedResponse));
    }
    
    // Fail to move a boulder with a wall behind it
    @Test
    public void testBoulderWall() {
    	DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("boulders","peaceful");
        
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player and boulders didn't move
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(4, 2), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(5, 2), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(6, 2), false), movedResponse));
    }

}
