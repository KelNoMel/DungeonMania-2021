package dungeonmania.entities.collectables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class KeyTest {
	@Test
	public void testKeyPickup() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("key-pickup","peaceful");
        DungeonResponse response = mania.tick(null, Direction.RIGHT);
        assertTrue(
        	ResponseHelp.inventoryEqual(
	    		Arrays.asList(
	    			"key"
	    		),
	    		response
    		)
        );
	}
	
	@Test
	public void testMultipleKey() {
		DungeonManiaController mania = new DungeonManiaController();
		List<String> oneKeyInventory = Arrays.asList("key");
        
		DungeonResponse response1;
		mania.newGame("key-door", "peaceful");
        response1 = mania.tick(null, Direction.UP);

        assertTrue(ResponseHelp.inventoryEqual(oneKeyInventory, response1));
        
        mania.tick(null, Direction.LEFT);
        response1 = mania.tick(null, Direction.DOWN);
        
        assertTrue(ResponseHelp.inventoryEqual(oneKeyInventory, response1));
        
        response1 = mania.tick(null, Direction.RIGHT);
        DungeonResponse response2 = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.dungeonEqual(response1, response2));
        
        response1 = mania.tick(null, Direction.DOWN);
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(0, 1), false), response1));
        
        response1 = mania.tick(null, Direction.UP);
        response2 = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.dungeonEqual(response1, response2));
        
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.RIGHT);
        response1 = mania.tick(null, Direction.RIGHT);
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), response1));
	}
}
