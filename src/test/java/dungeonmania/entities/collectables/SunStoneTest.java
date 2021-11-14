package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class SunStoneTest {
    @Test
	public void testSunStonePickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("sunstone-pickup","peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"sun_stone"
	    		),
	    		response
    		)
        );
	}
}
