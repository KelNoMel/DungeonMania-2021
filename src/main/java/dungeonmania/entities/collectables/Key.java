package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;
import dungeonmania.entities.statics.Door;
import dungeonmania.util.Position;

public class Key extends Entity {

	public Key(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "key", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		
	}

	public boolean unlockDoor() {
		Door.isUnlocked();
		this.setState(EntityState.DEAD);
		return true;
		
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
