package dungeonmania.entities.redstone;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MultipleRedstoneTest {
	@Test
	public void testMultipleRedstone() {
		DungeonManiaController mania = new DungeonManiaController();
        
		DungeonResponse response;
		
		mania.newGame("multiple-redstone", "peaceful");
        
		// Enable first switch
		response = mania.tick(null, Direction.RIGHT);
		
		// Check that all redstone is powered correctly
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(1, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(2, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(0, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(1, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire13", new Position(2, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(0, 2), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire13", new Position(1, 2), false), response));
		
		// Enable second switch
		
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.UP);
		response = mania.tick(null, Direction.LEFT);
		
		// Check that all redstone is powered correctly
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(1, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(2, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(0, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(1, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(2, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(0, 2), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(1, 2), false), response));
	
		// Disable first switch
		mania.tick(null, Direction.UP);
		mania.tick(null, Direction.UP);
		mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.LEFT);
		response = mania.tick(null, Direction.LEFT);
		
		// Check that all redstone is powered correctly
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire13", new Position(1, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(2, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire13", new Position(0, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(1, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(2, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire14", new Position(0, 2), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire15", new Position(1, 2), false), response));
	
		// Disable second switch
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		response = mania.tick(null, Direction.DOWN);
		
		// Check that all redstone is powered down
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(1, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(2, 0), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(0, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(1, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(2, 1), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(0, 2), false), response));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wire0", new Position(1, 2), false), response));
	}
}
