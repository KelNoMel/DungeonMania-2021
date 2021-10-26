package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Key extends Entity {
	private String keyId;

	public Key(Dungeon dungeon, Position position, String keyId) {
		super(dungeon, "key", position, false);
		this.keyId = keyId;
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
