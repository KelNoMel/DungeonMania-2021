package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class InvisibilityPotionTest {
	@Test
	public void testInvincibilityPotionPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("invisibility-potion-pickup","Peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"invisibility_potion"
	    		),
	    		response
    		)
        );
	}
}
