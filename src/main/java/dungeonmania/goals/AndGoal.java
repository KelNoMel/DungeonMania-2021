package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Dungeon;

public class AndGoal extends Goal {

	public AndGoal(Dungeon checkDungeon, Goal subgoalOne, Goal subgoalTwo) {
		super(checkDungeon, 2);
		add(subgoalOne);
		add(subgoalTwo);
	}

	public boolean checkGoal() {
		return (getChild(0).checkGoal() && getChild(1).checkGoal());
	}
	
	public String toString() {
		return "AND";
	}

	public String response() {
		boolean leftStatus = getChild(0).checkGoal();
		boolean rightStatus = getChild(1).checkGoal();
		
		if (leftStatus && rightStatus) {
			return "";
		} else if (leftStatus) {
			return getChild(1).response();
		} else if (rightStatus) {
			return getChild(0).response();
		} else {			
			return "( " + getChild(0).response() + " " + toString() + " " + getChild(1).response() + " )";
		}
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
