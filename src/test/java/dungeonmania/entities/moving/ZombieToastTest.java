package dungeonmania.entities.moving;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Position;

public class ZombieToastTest {
	@Test
	public void testPortalNoTeleport() {
		DungeonManiaController mania = new DungeonManiaController();
        
		for (int i = 0; i < 4; i++) {
			DungeonResponse res = mania.newGame("zombie-toast-portal","standard");
			assertTrue(ResponseHelp.getEntityOfType(res, "zombie_toast").getPosition().equals(new Position(1, 2)));			
		}
	}
}
