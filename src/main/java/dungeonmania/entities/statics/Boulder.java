package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Boulder extends Entity {

	public Boulder(Dungeon dungeon, Position position) {
		super(dungeon, "boulder", position, true);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
