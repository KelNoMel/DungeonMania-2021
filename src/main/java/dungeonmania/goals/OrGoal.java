package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Dungeon;

public class OrGoal extends Goal {

	public OrGoal(Dungeon checkDungeon, Goal subgoalOne, Goal subgoalTwo) {
		super(checkDungeon, 2);
		add(subgoalOne);
		add(subgoalTwo);
	}

	public boolean checkGoal() {
		return getChild(0).checkGoal() || getChild(1).checkGoal();
	}
	
	public String toString() {
		return "OR";
	}

	public String response() {
		return getChild(0).response() + toString() + getChild(1).response();
	}

	public JSONObject toJSON() {
		JSONObject andJSON = new JSONObject();
		andJSON.put("goal", toString());
		
		JSONArray subgoals = new JSONArray();
		subgoals.put(getChild(0).toJSON());
		subgoals.put(getChild(1).toJSON());
		andJSON.put("subgoals", subgoals);
		
		return andJSON;
	}

}
