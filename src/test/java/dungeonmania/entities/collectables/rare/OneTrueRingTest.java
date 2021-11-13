package dungeonmania.entities.collectables.rare;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class OneTrueRingTest {
	@Test
	public void testRingPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("one-true-ring","peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"the_one_ring"
	    		),
	    		response
    		)
        );
	}

	// Player would die if not using ring
	@Test
	public void testRingRevival() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("one-true-ring","standard");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
		
        mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
		mania.tick(null, Direction.NONE);
        d = mania.tick(null, Direction.NONE);
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), d));
	}
}
