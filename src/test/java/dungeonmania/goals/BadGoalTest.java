package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.Dungeon;

public class BadGoalTest {
	@Test
    public void treasureGoalTest() {
		Dungeon d = new Dungeon("advanced", "Hard");
    	assertThrows(IllegalArgumentException.class, ()->new BadGoal(d));
    }
}
