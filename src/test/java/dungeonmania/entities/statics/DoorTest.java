package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DoorTest {
	@Test
	public void testDoorKey() {
		DungeonManiaController mania = new DungeonManiaController();
        
		DungeonResponse response;
		
		response = mania.newGame("key-door", "Peaceful");
        
        assertTrue(ResponseHelp.dungeonEqual(response, mania.tick(null, Direction.RIGHT)));
        assertTrue(ResponseHelp.dungeonEqual(response, mania.tick(null, Direction.DOWN)));
        
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.RIGHT);
        
        mania.tick(null, Direction.DOWN);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(0, 0), false), response));
        
        response = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), response));
        
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.DOWN);
        response = mania.tick(null, Direction.DOWN);
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(0, 1), false), response));
        
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(),
	    		response
    		)
        );
	}
}
