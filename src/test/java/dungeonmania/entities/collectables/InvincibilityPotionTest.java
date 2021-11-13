package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvincibilityPotionTest {
	@Test
	public void testInvincibilityPotionPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("invincibility-potion-pickup","peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"invincibility_potion"
	    		),
	    		response
    		)
        );
	}

	// Player would die if not using potion
	@Test
	public void testInvinciblePotionConsumption() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("invincibility-potion-pickup","standard");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
		
        mania.tick(null, Direction.RIGHT);
		mania.tick("invincibility_potion", Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
        d = mania.tick(null, Direction.RIGHT);
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), d));
	}
}
