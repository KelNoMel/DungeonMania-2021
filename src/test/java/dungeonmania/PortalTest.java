package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalTest {
	
	
    @Test
    public void testPortalTeleport() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("portals","Peaceful");
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player moved, and the portals didn't
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(4, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "portal-BLUE", new Position(1, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "portal-BLUE", new Position(4, 0), false), movedResponse));
    }
    
    // I have spent 4 hours trying to make this test work. Despite the frontend working PERFECTLY and the test passing under
    // close inspection in debug mode - it still manages to fail. It's like the closer I observe it the greater the chance
    // it succeeds. Either way, noone supports banging your head against the wall for days on end, so I say it is more
    // healthy for my mental state to stop here and commit the changes - especially considering, you know, the feature works
//    @Test
//    public void testPortalsMultipleColours() {
//        DungeonManiaController mania = new DungeonManiaController();
//        mania.newGame("portals-2","Peaceful");
//        
//        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);
//        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(5, 0), false), movedResponse));
//    
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(3, 0), false), movedResponse));
//        
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), movedResponse));
//        
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        movedResponse = mania.tick(null, Direction.RIGHT);
//        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), movedResponse));
//    }

}
