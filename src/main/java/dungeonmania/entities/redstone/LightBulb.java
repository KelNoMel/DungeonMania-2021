package dungeonmania.entities.redstone;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.RedstoneComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class LightBulb extends Entity {

	public RedstoneComponent redstoneComponent = new RedstoneComponent(this, 1);
	
	public LightBulb(Dungeon dungeon, Position position) {
		super(dungeon, "light_bulb", position, false, EntityUpdateOrder.OTHER);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}
	
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	
	private String activationState() {
		if (redstoneComponent.isActivated()) {
			return "on";
		}
		return "off";
	}
	
	@Override
	public EntityResponse response() {
    	return new EntityResponse(getId(), getType() + "_" + activationState(), getPosition(), getInteractable());
    }

}
