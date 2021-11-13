package dungeonmania.goals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.Dungeon;

public class BadGoalTest {
	@Test
    public void treasureBadGoalTest() {
		Dungeon d = new Dungeon("advanced", "hard");
    	assertThrows(IllegalArgumentException.class, ()->new BadGoal(d));
    }
	
	@Test
    public void treasureBadGoalJSONTest() {
		assertThrows(IllegalArgumentException.class, ()->new Dungeon("bad-goal", "hard"));
    }
}
