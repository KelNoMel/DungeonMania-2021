package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Door;
import dungeonmania.util.Position;

public class Key extends Entity {
	private String keyId;
	private boolean used;

	public Key(Dungeon dungeon, Position position, String keyId) {
		super(dungeon, "key", position, false);
		this.keyId = keyId;
		used = false;
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		
	}

	private boolean unlockDoor() {
		if (Door.getDoorList().stream().filter(id -> id == keyId) != null) {
			updateEntity();
			return true;
		} else {
			return false;
		}
		
	}

}
