package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Key extends Entity {

	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	
	private int keyNumber;
	private String colour;
	
	public Key(Dungeon dungeon, Position position) {
		super(dungeon, "key", position, false, EntityUpdateOrder.OTHER);
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("key", keyNumber);
	}
	
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		keyNumber = entitySpecificData.getInt("key");
		if (keyNumber % 2 == 0) {
			colour = "gold";
		} else {
			colour = "silver";
		}
		
	}
	
	public int getKeyNumber() {
		return keyNumber;
	}
	
	@Override
	public EntityResponse response() {
    	return new EntityResponse(getId(), getType() + "-" + colour, getPosition(), getInteractable());
    }
}
