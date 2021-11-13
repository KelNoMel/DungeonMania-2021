package dungeonmania.entities.redstone;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

/**
 * Door block parts of the map until they are opened with their unique key.
 */
public class SwitchDoor extends Entity {
	
	public RedstoneComponent redstoneComponent = new RedstoneComponent(this, 1);
	
	public SwitchDoor(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "switch_door", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	/**
	 * Save the door and it's corresponding keynumber in a json uniquely 
	 * recognisable format.
	 */
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	
	/**
	 * Load in the json using the our defined format.
	 */
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

	
	
	public EntityResponse response() {
		if (isUnlocked()) {
			return new EntityResponse(getId(), getType() + "_unlocked", getPosition(), getInteractable());			
		}
		return new EntityResponse(getId(), getType() + "_locked", getPosition(), getInteractable());			
    }
	
	/**
	 * Attempt to open / unlock a door.
	 * @return true if door is unlocked after an attempt, false otherwise
	 */
	public boolean isUnlocked() {
		return redstoneComponent.isActivated();
	}
}
