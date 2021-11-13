package dungeonmania.entities.statics;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawnerTest {
	@Test
	public void basicZombieToast() {
		DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("zombie-toast-test", "standard");
        
        for (int i = 0; i < 19; i++) {
        	mania.tick(null, Direction.DOWN);        	
        }
        
        DungeonResponse response = mania.tick(null, Direction.UP);
        
//        for (EntityResponse r : response.getEntities()) {
//        	System.out.println(r.getType() + r.getPosition());
//        }
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast", new Position(4, 0), false), response));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), true), response));
	}
}
