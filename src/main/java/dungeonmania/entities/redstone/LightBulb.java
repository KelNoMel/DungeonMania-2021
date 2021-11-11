package dungeonmania.entities.redstone;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.RedstoneConduitComponent;
import dungeonmania.entities.Entity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class LightBulb extends Entity {

	public RedstoneConduitComponent redstoneComponent = new RedstoneConduitComponent(this, 1);
	
	public LightBulb(Dungeon dungeon, String type, Position position, JSONObject entityData) {
		super(dungeon, "light_bulb", position, false, entityData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {

	}
	
	@Override
	public EntityResponse response() {
    	return new EntityResponse(getId(), getType() + redstoneComponent, getPosition(), getInteractable());
    }

}
