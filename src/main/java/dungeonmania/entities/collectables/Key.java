package dungeonmania.entities.collectables;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;
import dungeonmania.entities.statics.Door;
import dungeonmania.util.Position;

public class Key extends Entity {

	private String keyId;
	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	private EntityState state;

	public Key(Dungeon dungeon, Position position, String keyId) {
		super(dungeon, "key", position, false);
		this.keyId = keyId;
		this.state = EntityState.ACTIVE;
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		
	}

	public boolean unlockDoor() {
		if (Door.getDoorList().stream().filter(id -> id == keyId) != null) {
			Door.isUnlocked();
			this.setState(EntityState.DEAD);
			return true;
		} else {
			return false;
		}
		
	}

	 

}
