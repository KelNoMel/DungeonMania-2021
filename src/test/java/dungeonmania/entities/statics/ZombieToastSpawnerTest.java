package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawnerTest {
	@Test
	public void basicZombieToastSpawn() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("zombie-toast-test", "standard");
        
        for (int i = 0; i < 19; i++) {
        	mania.tick(null, Direction.DOWN);        	
        }
        
        DungeonResponse response = mania.tick(null, Direction.UP);
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast", new Position(4, 0), false), response));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), true), response));
	}
	
	@Test
	public void zombieToastSpawnerBreak() {
		DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse response = mania.newGame("zombie-toast-spawner-break", "standard");
        
        String spawnerId = null;
        for (EntityResponse e : response.getEntities()) {
        	if (e.getType().equals("zombie_toast_spawner")) {
        		spawnerId = e.getId();
        		break;
        	}
        }
        
        final String spawnerIdActual = spawnerId;
        // Out of range
        assertThrows(InvalidActionException.class, ()->mania.interact(spawnerIdActual));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), true), response));
        
        // No weapon
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.DOWN);
        response = mania.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, ()->mania.interact(spawnerIdActual));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), true), response));
        
        // Destroyed
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.LEFT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        assertDoesNotThrow(()->mania.interact(spawnerIdActual));
        response = mania.tick(null, null);
        assertFalse(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), true), response));
	}
}
