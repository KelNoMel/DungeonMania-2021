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
        System.out.println("1");
        for (Entity entity : entityList) {
            if (entity instanceof FloorSwitch) {
                System.out.println("hi");
                floorSwitch = (FloorSwitch) entity;
                System.out.println(floorSwitch.isTriggered());
                if (!floorSwitch.isTriggered()) return false;
            }
        }
        return true;
    }
    
}
