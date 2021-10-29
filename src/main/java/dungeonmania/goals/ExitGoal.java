package dungeonmania.goals;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Exit;

public class ExitGoal extends Goal{

    public ExitGoal (Dungeon dungeon) {
        super(dungeon, "exit");

    }

    
    public boolean checkGoal() {
        //TODO:
        Exit exit;
        for (Entity entity : dungeon.getEntities()) { 
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
    
}
