package dungeonmania;

public abstract class Goal {
    private String goalType;
    private Dungeon dungeon;

    public Goal (Dungeon dungeon, String goalType) {
        this.goalType = goalType;
        this.dungeon = dungeon;
    }

    public boolean checkGoal () {
        // TODO: 
        return false;
    }
}
