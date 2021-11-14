package dungeonmania.battles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleItemsTest {
    @Test
	public void testPlayerDiesNoItems() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("","standard");
        mania.tick(null, Direction.RIGHT);
	}
    
    @Test
	public void testArmourDurability() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("","standard");
        mania.tick(null, Direction.RIGHT);
	}

    @Test
	public void testShieldDurability() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("","standard");
        mania.tick(null, Direction.RIGHT);
	}

    @Test
    public void testArmourCombo() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("sceptre-basic","standard");
        mania.tick(null, Direction.RIGHT);
    }

    @Test
    public void testBowDurability() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("","standard");
        mania.tick(null, Direction.RIGHT);
    }

    @Test
	public void testSwordDurability() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("","standard");
        mania.tick(null, Direction.RIGHT);
	}

    @Test
	public void testWeaponCombo() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("sceptre-basic","standard");
        mania.tick(null, Direction.RIGHT);
	}
}
