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
        //TODO: double check: when treasure is collected into inventory, is it no longer in dungeon entity list? 
        List<Entity> entityList = dungeon.getEntities();
        
        for (Entity entity : entityList) {
            if (entity instanceof Treasure) 
                return false;
        }
        return true;
    }
    
}
