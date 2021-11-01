package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Dungeon;

public class BadGoal extends Goal {

	public BadGoal(Dungeon checkDungeon) {
		super(checkDungeon, 1);
		add(new BoulderGoal(checkDungeon));
		add(new BoulderGoal(checkDungeon));
	}

	public boolean checkGoal() {
		return false;
	}

	public String toString() {
		return null;
	}

	public String response() {
		return null;
	}

	public JSONObject toJSON() {
		return null;
	}

}
