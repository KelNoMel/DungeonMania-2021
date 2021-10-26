package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Door extends Entity {

	private boolean isUnlocked;
	private String doorId;

	public Door(Dungeon dungeon, Position position, String doorId) {
		super(dungeon, "door", position, true);
		isUnlocked = false;
		this.doorId = doorId;
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		isUnlocked = true;
	}

	protected String getDoorId() {
		return doorId;
	}

	// if statics.door.isUnlocked = true {move}

}
