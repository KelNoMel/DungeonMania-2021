package dungeonmania.goals;

import dungeonmania.Dungeon;

public abstract class Goal {
    protected String goalType;
    protected Dungeon dungeon;

    public Goal (Dungeon dungeon, String goalType) {
        this.goalType = goalType;
        this.dungeon = dungeon;
    }

    public abstract boolean checkGoal();
}
