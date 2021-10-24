package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Portal extends Entity {

	public Portal(Dungeon dungeon, Position position) {
		super(dungeon, "Portal", position, true);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
