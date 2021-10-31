package dungeonmania.goals;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.entities.statics.ZombieToastSpawner;

public class EnemiesGoal extends Goal {

    public EnemiesGoal (Dungeon dungeon) {
        super(dungeon, 0);
    }

    public boolean checkGoal() {
        // spider, zombietoast, zombietoastspawner
        List<Entity> entityList = getDungeon().getEntities();
        
        for (Entity entity : entityList) {
            if (entity instanceof Spider || entity instanceof ZombieToast || entity instanceof ZombieToastSpawner) 
                return false;
        }
        return true;
    }

	public String toString() {
		return "enemies";
	}

	public String response() {
		return ":" + toString();
	}
	
	public JSONObject toJSON() {
		JSONObject enemiesJSON = new JSONObject();
		enemiesJSON.put("goal", "enemies");
		return enemiesJSON;
	}
}
