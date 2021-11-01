package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class HealthPotionTest {
	@Test
	public void testhealthPotionPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("health-potion-pickup","Peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"health_potion"
	    		),
	    		response
    		)
        );
	}

	// Player would die if not using potion
	@Test
	public void testhealthPotionConsumption() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("health-potion-pickup","Peaceful");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
		mania.tick("health_potion", Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
        d = mania.tick(null, Direction.RIGHT);
		assertTrue(assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), d));
	}
}
