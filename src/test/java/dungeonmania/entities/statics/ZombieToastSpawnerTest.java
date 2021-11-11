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
        
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        mania.tick(null, Direction.UP);
        DungeonResponse response = mania.tick(null, Direction.UP);
        
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast", new Position(4, 0), false), response));
        assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "zombie_toast_spawner", new Position(4, 0), false), response));
	}
}
