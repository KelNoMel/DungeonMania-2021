package dungeonmania.entities.statics;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Key;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Door extends Entity {
	private boolean isUnlocked;
	private String doorId;

	private int linkedKeyNumber;
	
	public Door(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "door", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
	}

	public String getDoorId() {
		return doorId;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("key", linkedKeyNumber);
		baseJSON.put("locked", isUnlocked);
	}
	
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		linkedKeyNumber = entitySpecificData.getInt("key");
		if (entitySpecificData.has("locked")) {
			isUnlocked = entitySpecificData.getBoolean("locked");
		} else {
			isUnlocked = false;
		}
	}
	
	@Override
	public EntityResponse response() {
		if (isUnlocked) {
			return new EntityResponse(getId(), getType() + "-unlocked", getPosition(), getInteractable());
		}
		
		String colour;
		if (linkedKeyNumber % 2 == 0) {
			colour = "gold";
		} else {
			colour = "silver";
		}
    	return new EntityResponse(getId(), getType() + "-locked-" + colour, getPosition(), getInteractable());
    }
	
	public boolean attemptUnlock() {

		if (isUnlocked) return true;
		
		for (Entity e : getDungeon().getPlayer().getInventory()) {
			if (e instanceof Key && ((Key)e).getKeyNumber() == linkedKeyNumber) {
				e.setState(EntityState.DEAD);
				isUnlocked = true;
			}
		}
		
		return isUnlocked;
	}
}
