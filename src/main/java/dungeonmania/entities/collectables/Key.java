package dungeonmania.entities.collectables;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
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
		Position position = getPosition();
		List<Entity> Entities = dungeon.getEntitiesAtPosition(position);
		
		for (Entity e : Entities) {
			if (e instanceof Door) {
				unlockDoor();
			}
			if (e instanceof Player) {
				// addInventory(this);
			}
		}
		if (unlockDoor() == true) {
			used = true;
		}
	}

	public boolean unlockDoor() {
		if (Door.getDoorList().stream().filter(id -> id == keyId) != null) {
			Door.isUnlocked();
			return true;
		} else {
			return false;
		}
		
	}

	 

}
