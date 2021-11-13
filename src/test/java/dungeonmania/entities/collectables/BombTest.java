package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BombTest {
	@Test
	public void testBombPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bomb-pickup","peaceful");
        DungeonResponse response = mania.tick(null, Direction.LEFT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"bomb"
	    		),
	    		response
    		)
        );
	}

	@Test
	public void testBombPlace() {
		List<String> empty = new ArrayList<String>();
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bomb-pickup","peaceful");
        mania.tick(null, Direction.LEFT);
		mania.tick(null, Direction.LEFT);
		DungeonResponse d = mania.tick("bomb", Direction.NONE);
		// Bomb is out of inventory, bomb and player occupy same tile
		assertTrue(ResponseHelp.inventoryEqual(empty, d));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(-4, 0), false), d));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "bomb", new Position(-4, 0), false), d));
		d = mania.tick(null, Direction.LEFT);
		// Player still hasn't picked up bomb
		assertTrue(ResponseHelp.inventoryEqual(empty, d));
		d = mania.tick(null, Direction.RIGHT);
		// Player picks up bomb again
		assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"bomb"
	    		),
	    		d
    		)
        );
	}

	// Makes sure that wire activation works, also tests bomb effect radius
	@Test
	public void testWireActivation() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bomb-pickup","peaceful");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
		// Assert that only immediate walls are blown up
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "bomb", new Position(15, 0), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wire", new Position(14, 0), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(14, 1), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(14, -1), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(15, 1), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(15, -1), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(16, 1), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(16, 0), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(16, -1), false), d));
		// Assert entities 2 blocks away still exist
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(15, 2), false), d));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(15, -2), false), d));
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(16, 2), false), d));
	}

	// Tests adjacent switch activation, also makes sure player is immune
	@Test
	public void testPlayerImmunity() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bomb-pickup","peaceful");
        mania.tick(null, Direction.UP);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.DOWN);
		mania.tick(null, Direction.DOWN);
		DungeonResponse d = mania.tick(null, Direction.DOWN);

		// Assert only immediate walls are blown up
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "bomb", new Position(0, 3), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "switch", new Position(-1, 3), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(0, 4), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(0, 2), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(1, 2), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(1, 3), false), d));
		assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "wall", new Position(1, 4), false), d));
		// Assert player still exists
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(-1, 2), false), d));
	}
}
