package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Exit;

public class ExitGoal extends Goal {

    public ExitGoal (Dungeon dungeon) {
        super(dungeon, 0);
    }

    public boolean checkGoal() {
        Exit exit;
        for (Entity entity : getDungeon().getEntities()) { 
            if (entity instanceof Exit) {
                exit = (Exit) entity;
                if (exit.playerAtExit()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public String response() {
    	if (checkGoal()) return "";
    	return ":exit";
    }
    
	public String toString() {
		return "exit";
	}
	
	public JSONObject toJSON() {
		JSONObject exitJSON = new JSONObject();
		exitJSON.put("goal", "exit");
		return exitJSON;
	}
    
}
