package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class FloorSwitch extends Entity {
	private boolean isTriggered;

	public FloorSwitch(Dungeon dungeon, Position position) {
		super(dungeon, "switch", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		for (Entity entity : dungeon.getEntitiesAtPosition(getPosition())) {
			if (entity instanceof Boulder) isTriggered = true;
			else isTriggered = false;
		}

	}

	public boolean isTriggered() {
		return isTriggered;
	}




}
