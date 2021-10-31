package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Dungeon;

public class DefaultGoal extends Goal {
   
	public DefaultGoal (Dungeon dungeon) {
        super(dungeon, 0);
    }
    
    public boolean checkGoal() {
        return false;
    }

    public String response() {
    	return "";
    }
    
	public String toString() {
		return "default";
	}

	public JSONObject toJSON() {
		JSONObject defaultJSON = new JSONObject();
		defaultJSON.put("default", toString());
		return defaultJSON;
	}
    
}
