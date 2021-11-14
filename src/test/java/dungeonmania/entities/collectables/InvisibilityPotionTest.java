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

public class InvisibilityPotionTest {
	@Test
	public void testInvisibilityPotionPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("invisibility-potion-pickup","peaceful");
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

	// Player would die if not using potion
	@Test
	public void testInvisiblePotionConsumption() {
		DungeonManiaController mania = new DungeonManiaController();
		DungeonResponse d = mania.newGame("invisibility-potion-pickup","standard");
        
		String potId = getPotId(d);
		
		mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
		mania.tick(potId, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
		mania.tick(null, Direction.RIGHT);
        d = mania.tick(null, Direction.RIGHT);
		assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(7, 0), false), d));
	}
	
	private String getPotId(DungeonResponse d) {
        for (EntityResponse r : d.getEntities()) {
        	if (r.getType().equals("invisibility_potion")) {
        		return r.getId();
        	}
        }
        return null;
	}
}
