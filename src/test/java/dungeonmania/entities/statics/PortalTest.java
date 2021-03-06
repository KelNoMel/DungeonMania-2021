package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTest {
	
	
    @Test
    public void testPortalTeleport() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("portals","peaceful");
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player moved, and the portals didn't
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(4, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "portal-BLUE", new Position(1, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "portal-BLUE", new Position(4, 0), false), movedResponse));
    }
    
    @Test
    public void testPortalsMultipleColours() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("portals-2","peaceful");
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(5, 0), false), movedResponse));
    
        movedResponse = mania.tick(null, Direction.RIGHT);
        movedResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(3, 0), false), movedResponse));
        
        movedResponse = mania.tick(null, Direction.RIGHT);
        movedResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), movedResponse));
        
        movedResponse = mania.tick(null, Direction.RIGHT);
        movedResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), movedResponse));
    }

}
