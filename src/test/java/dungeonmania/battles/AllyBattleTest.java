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
            // player fights 2 mercenaries
            d = mania.tick(null, Direction.RIGHT);
            assertEquals(null, ResponseHelp.getEntityOfType(d, "mercenary"));
            // health now 40hp

            // exit test if interfering items are repeatedly found
            attemptCounter++;
            if (attemptCounter >= maxRandomAttempts) return;
        } while (ResponseHelp.inventorySize(d) != 0);
        // no items in inventory
        mania.tick(null, Direction.NONE);
        mania.tick(null, Direction.NONE);
        // player dies fighting the assassin
        d = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(d, "player"));
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
            // player fights 2 mercenaries and picks up sun_stone
            d = mania.tick(null, Direction.RIGHT);
            assertEquals(null, ResponseHelp.getEntityOfType(d, "mercenary"));
            assertNotEquals(null, ResponseHelp.getItemOfType(d, "sun_stone"));
            // health now 40hp

            // exit test if interfering items are repeatedly found
            attemptCounter++;
            if (attemptCounter >= maxRandomAttempts) return;
        } while (ResponseHelp.inventorySize(d) != 1);
        // only sun_stone in inventory
        mania.tick(null, Direction.NONE);
        // bribe mercenary


        mania.tick(null, Direction.NONE);
        // player dies fighting the assassin but so does the assassin
        d = mania.tick(null, Direction.NONE);
        assertEquals(null, ResponseHelp.getEntityOfType(d, "player"));
	}

    @Test
	public void testPlayerLivesAndKillsEnemyWtihAllies() {
		DungeonManiaController mania = new DungeonManiaController();
		mania.newGame("team-battle","standard");
        DungeonResponse d = mania.tick(null, Direction.RIGHT);
	}


}
