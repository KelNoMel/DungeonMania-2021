package dungeonmania;

public enum GoalCondition {
    AND, OR;

    public static GoalCondition getGoalCondition(String goalCondition) throws IllegalArgumentException {
    	switch (goalCondition) {
    	case "AND":
    		return AND;
    	case "OR":
    		return OR;
    	default:
    		throw new IllegalArgumentException();
    	}
    }
}
