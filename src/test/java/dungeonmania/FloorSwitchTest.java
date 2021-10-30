package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FloorSwitchTest {
    
    @Test
    public void testSwitchPressed() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("floorswitch","Peaceful");

        // check state
        // TODO: bouldergoal.checkgoal
        
        DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // Check that the player and boulder moved 
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "boulder", new Position(2, 0), false), movedResponse));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "switch", new Position(2, 0), false), movedResponse));
        
        // check state
        // TODO: bouldergoal.checkgoal

    }

}
