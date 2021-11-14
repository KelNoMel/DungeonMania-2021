package dungeonmania.battles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

//import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class AllyBattleTest {
    @Test
	public void testAllyFight() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("ally-solo-fight","standard");
        // pickup treasure
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
        // bribe either of the two mercenaries on the same tile and in range
        String mercID = ResponseHelp.getEntityOfType(d, "mercenary").getId();
        assertDoesNotThrow(()->mania.interact(mercID));
        // ally and enemy mercenary kill each other
        d = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(d, "mercenary"));
	}

    @Test
	public void testPlayerDiesNoAlly() {
		DungeonManiaController mania;
        DungeonResponse d;
        int maxRandomAttempts = 100;
        int attemptCounter = 0;
        do {
            mania = new DungeonManiaController();
            mania.newGame("ally-support-none","standard");
            // player fights mercenary
            d = mania.tick(null, Direction.RIGHT);
            // mercenary is dead
            assertEquals(null, ResponseHelp.getEntityOfType(d, "mercenary"));
            // player picks up 3 armour
            d = mania.tick(null, Direction.RIGHT);
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "armour"));
            // health now 70hp

            // exit test if interfering items are repeatedly found
            attemptCounter++;
            if (attemptCounter >= maxRandomAttempts) return;
        } while (ResponseHelp.inventorySize(d) != 3);
        // only armour in inventory
        mania.tick(null, Direction.NONE);
        d = mania.tick(null, Direction.NONE);
        // player dies fighting the assassin
        assertEquals(null, ResponseHelp.getEntityOfType(d, "player"));
        // assassin lives
        assertNotEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));

	}

    @Test
	public void testPlayerDiesButKillEnemyWtihAlly() {
        DungeonManiaController mania;
        DungeonResponse d;
        int maxRandomAttempts = 100;
        int attemptCounter = 0;
        do {
            mania = new DungeonManiaController();
            mania.newGame("ally-support","standard");
            // player fights mercenary
            d = mania.tick(null, Direction.RIGHT);
            // player picks up 3 armour and sunstone
            d = mania.tick(null, Direction.RIGHT);
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "armour"));
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "sun_stone"));
            // health now 70hp
            
            // exit test if interfering items are repeatedly found
            attemptCounter++;
            if (attemptCounter >= maxRandomAttempts) return;
        } while (ResponseHelp.inventorySize(d) != 4);
        // only 3 armour and sun_stone in inventory
        // bribe mercenary
        String mercID = ResponseHelp.getEntityOfType(d, "mercenary").getId();
        mania.interact(mercID);
        // player dies fighting the assassin
        // assassin dies by getting attacked by both the player and the ally
        d = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(d, "player"));
        assertEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));
	}

    @Test
	public void testPlayerLivesAndKillsEnemyWtihAllies() {
        DungeonManiaController mania;
        DungeonResponse d;
        int maxRandomAttempts = 100;
        int attemptCounter = 0;
        do {
            mania = new DungeonManiaController();
            mania.newGame("team-battle","standard");
            // player fights mercenary
            d = mania.tick(null, Direction.RIGHT);
            // player picks up 3 armour and sunstone
            d = mania.tick(null, Direction.RIGHT);
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "armour"));
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "sun_stone"));
            // health now 70hp
            
            // exit test if interfering items are repeatedly found
            attemptCounter++;
            if (attemptCounter >= maxRandomAttempts) return;
        } while (ResponseHelp.inventorySize(d) != 4);
        // only 3 armour and sun_stone in inventory
        List<String> mercenaryIds = ResponseHelp.getAllEntityOfTypeIds(d, "mercenary");
        // bribe mercenary
        mania.interact(mercenaryIds.get(0));
        // bribe second mercenary
        mania.interact(mercenaryIds.get(1));
        // player kills assassin with support of 2 allies
        d = mania.tick(null, Direction.NONE);
        assertNotEquals(null, ResponseHelp.getEntityOfType(d, "player"));
        assertEquals(null, ResponseHelp.getEntityOfType(d, "assassin"));
	}


}
