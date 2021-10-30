package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.entities.statics.ZombieToastSpawner;

public class EnemiesGoal extends Goal{

    public EnemiesGoal (Dungeon dungeon) {
        super(dungeon, "enemies");

    }

    public boolean checkGoal() {
        // spider, zombietoast, zombietoastspawner
        List<Entity> entityList = dungeon.getEntities();
        
        for (Entity entity : entityList) {
            if (entity instanceof Spider || entity instanceof ZombieToast || entity instanceof ZombieToastSpawner) 
                return false;
        }
        return true;
    }
}
