package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;

public abstract class Goal {
	private List<Goal> components = new ArrayList<Goal>();
	private int requiredChildren;
	private Dungeon checkDungeon;
	
	public Goal(Dungeon checkDungeon, int requiredChildren) {
		this.requiredChildren = requiredChildren;
		this.checkDungeon = checkDungeon;
	}
	
	public abstract boolean checkGoal();
	public abstract String toString();
	public abstract String response();
	public abstract JSONObject toJSON();

	protected final void add(Goal c) {
		if (components.size() >= requiredChildren) {			
			throw new IllegalArgumentException("You cannot add more than " + requiredChildren + " subgoals");
		}
		components.add(c);
	}
	
	protected final void remove(Goal c) {
		components.remove(c);
	}
	
	protected final Goal getChild(int childIndex) {
		if (childIndex < components.size()) {
			return components.get(childIndex);
		}
		throw new IllegalArgumentException("That component index is invalid");
	}
	
	protected final Dungeon getDungeon() {
		return checkDungeon;
	}
}
