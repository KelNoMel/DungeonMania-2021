package dungeonmania.battles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class BattleItemsTest {
    // @Test
	// public void testPlayerDiesNoItems() {
	// 	DungeonManiaController mania = new DungeonManiaController();
	// 	mania.newGame("","standard");
    //     mania.tick(null, Direction.RIGHT);
	// }
    
    // @Test
	// public void testArmourDurability() {
	// 	DungeonManiaController mania = new DungeonManiaController();
	// 	mania.newGame("","standard");
    //     mania.tick(null, Direction.RIGHT);
	// }

    // @Test
	// public void testShieldDurability() {
	// 	DungeonManiaController mania = new DungeonManiaController();
	// 	mania.newGame("","standard");
    //     mania.tick(null, Direction.RIGHT);
	// }

    // @Test
    // public void testArmourCombo() {
    //     DungeonManiaController mania = new DungeonManiaController();
    //     mania.newGame("sceptre-basic","standard");
    //     mania.tick(null, Direction.RIGHT);
    // }

    @Test
    public void testBowBasic() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bow-vs-assassin","standard");
        mania.tick(null, Direction.RIGHT);
        assertDoesNotThrow(() -> mania.build("bow"));
        DungeonResponse d = mania.tick(null, Direction.NONE);
        // player kills assassin
        assertEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));
    }

    @Test
    public void testBowDurability() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("bow-vs-horde","standard");
        mania.tick(null, Direction.RIGHT);
        // build bow
        assertDoesNotThrow(() -> mania.build("bow"));
        DungeonResponse d = mania.tick(null, Direction.NONE);
        // player heals
        List<String> potionIds = ResponseHelp.getAllItemOfTypeIds(d, "health_potion");

        // use potion and kill assassin
        mania.tick(potionIds.get(0), Direction.NONE);
        // use potion and kill assassin
        mania.tick(potionIds.get(1), Direction.NONE);

        // player kills third assassin
        d = mania.tick(null, Direction.NONE);
        d = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));
        // bow is now broken
        assertEquals(null, ResponseHelp.getItemOfType(d, "bow"));
    }

    // @Test
	// public void testSwordDurability() {
	// 	DungeonManiaController mania = new DungeonManiaController();
	// 	mania.newGame("","standard");
    //     mania.tick(null, Direction.RIGHT);
	// }

    // @Test
	// public void testWeaponCombo() {
	// 	DungeonManiaController mania = new DungeonManiaController();
	// 	mania.newGame("sceptre-basic","standard");
    //     mania.tick(null, Direction.RIGHT);
	// }
}
