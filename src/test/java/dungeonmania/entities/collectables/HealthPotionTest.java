package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HealthPotionTest {
	@Test
	public void testhealthPotionPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("health-potion-pickup","peaceful");
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
	public void testHealthPotionConsumption() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("health-potion-pickup","standard");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
		
        String id = "";
        for (ItemResponse e : d.getInventory()) {
        	if (e.getType().equals("health_potion")) {
        		id = e.getId();
        	}
        }
        
        mania.tick(id, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
        d = mania.tick(null, Direction.RIGHT);
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), d));
	}
}
