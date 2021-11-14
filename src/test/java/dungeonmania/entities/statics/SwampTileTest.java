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

        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("swamp","peaceful");
        
        DungeonResponse moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1,0), false), moveResponse));
        
        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), moveResponse));
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "swamp_tile", new Position(2, 0), false), moveResponse));
        
        // No movement for 3 ticks 
        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), moveResponse));

        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), moveResponse));

        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(2, 0), false), moveResponse));
        
        // Moved off swamp tile
        moveResponse = mania.tick(null, Direction.RIGHT);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(3, 0), false), moveResponse));
        
    }
    
}
