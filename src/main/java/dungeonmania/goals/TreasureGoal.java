package dungeonmania.goals;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Treasure;

public class TreasureGoal extends Goal {
    
    public TreasureGoal (Dungeon dungeon) {
        super(dungeon, 0);
    }

    public boolean checkGoal() {
        List<Entity> entityList = getDungeon().getEntities();
        
        for (Entity entity : entityList) {
            if (entity instanceof Treasure) {
            	return false;
            }
        }
        return true;
    }

    public String response() {
    	if (checkGoal()) return "";
		return ":treasure";
	}
    
	public String toString() {
		return "treasure";
	}

	public JSONObject toJSON() {
		JSONObject treasureJSON = new JSONObject();
		treasureJSON.put("goal", "treasure");
		return treasureJSON;
	}
	
}
