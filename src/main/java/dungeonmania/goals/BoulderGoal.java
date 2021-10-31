package dungeonmania.goals;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.FloorSwitch;

public class BoulderGoal extends Goal {
   
	public BoulderGoal (Dungeon dungeon) {
        super(dungeon, 0);
    }
    
    public boolean checkGoal() {
        List<Entity> entityList = getDungeon().getEntities();
        FloorSwitch floorSwitch;
        for (Entity entity : entityList) {
            if (entity instanceof FloorSwitch) {
                floorSwitch = (FloorSwitch) entity;
                if (!floorSwitch.isTriggered()) return false;
            }
        }
        return true;
    }

    public String response() {
    	if (checkGoal()) return "";
    	return ":boulder";
    }
    
	public String toString() {
		return "boulders";
	}

	public JSONObject toJSON() {
		JSONObject boulderJSON = new JSONObject();
		boulderJSON.put("goal", toString());
		return boulderJSON;
	}
    
}
