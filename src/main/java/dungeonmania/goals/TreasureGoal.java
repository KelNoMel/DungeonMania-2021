package dungeonmania.goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Treasure;

public class TreasureGoal extends Goal{
    
    public TreasureGoal (Dungeon dungeon) {
        super(dungeon, "treasure");

    }

    
    public boolean checkGoal() {
        List<Entity> entityList = dungeon.getEntities();
        
        for (Entity entity : entityList) {
            if (entity instanceof Treasure) 
                return false;
        }
        return true;
    }
    
}
