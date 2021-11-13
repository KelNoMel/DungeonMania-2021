package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTest {

    @Test
    public void testSwampTile() {
        // TODO: test movement with AIs
        // just testing that it is storing correct movement factor and that it is created properly right now

        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("swamp","Peaceful");
        
        DungeonResponse moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1,0), false), moveResponse));
        
        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), moveResponse));
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "swamp_tile", new Position(2, 0), false), moveResponse));
        
        // mania.tick(null, Direction.DOWN);
        // mania.tick(null, Direction.DOWN);
        // moveResponse = mania.tick(null, Direction.DOWN);
        // assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 3), false), moveResponse));
    }
    
}
