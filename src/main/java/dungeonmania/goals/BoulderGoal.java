package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.FloorSwitch;

public class BoulderGoal extends Goal{
    public BoulderGoal (Dungeon dungeon) {
        super(dungeon, "boulder");

    }

    
    public boolean checkGoal() {
        List<Entity> entityList = dungeon.getEntities();
        FloorSwitch floorSwitch;
        for (Entity entity : entityList) {
            if (entity instanceof FloorSwitch) {
                floorSwitch = (FloorSwitch) entity;
                if (!floorSwitch.isTriggered()) return false;
            }
                
        }
        return true;
    }
    
}
