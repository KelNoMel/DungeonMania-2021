package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * Door block parts of the map until they are opened with their unique key.
 */
public class Door extends Entity {
	private boolean isUnlocked;
	private String doorId;

	private int linkedKeyNumber;
	
	public Door(Dungeon dungeon, Position position) {
		super(dungeon, "door", position, false, EntityUpdateOrder.OTHER);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	/**
	 * Save the door and it's corresponding keynumber in a json uniquely 
	 * recognisable format.
	 */
	public void saveJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("key", linkedKeyNumber);
		baseJSON.put("locked", isUnlocked);
	}
	
	/**
	 * Load in the json using the our defined format.
	 */
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		linkedKeyNumber = entitySpecificData.getInt("key");
		if (entitySpecificData.has("locked")) {
			isUnlocked = entitySpecificData.getBoolean("locked");
		} else {
			isUnlocked = false;
		}
	}
	
	public String getDoorId() {
		return doorId;
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
	
	/**
	 * Attempt to open / unlock a door.
	 * @return true if door is unlocked after an attempt, false otherwise
	 */
	public boolean attemptUnlock() {
		if (isUnlocked) return true;
		
		for (Entity e : getDungeon().getPlayer().getInventory()) {
			if (e instanceof Key && ((Key)e).getKeyNumber() == linkedKeyNumber) {
				e.setState(EntityState.DEAD);
				isUnlocked = true;
			}
			if (e instanceof SunStone) {
				isUnlocked = true;
			}
		}
		
		return isUnlocked;
	}
}
