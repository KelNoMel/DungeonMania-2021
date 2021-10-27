package dungeonmania.goals;

import dungeonmania.Dungeon;

public class ExitGoal extends Goal{

    public ExitGoal (Dungeon dungeon, String goalType) {
        super(dungeon, goalType);

    }

    
    public boolean checkGoal() {
        //TODO:
        // getEntitiesAtPosition()
        return false;
    }
    
}
