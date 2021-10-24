package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class FloorSwitch extends Entity {

	public FloorSwitch(Dungeon dungeon, Position position) {
		super(dungeon, "switch", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
