package dungeonmania.entities.redstone;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.RedstoneComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Wire extends Entity {

	public RedstoneComponent redstoneComponent = new RedstoneComponent(this, 1);
	
	public Wire(Dungeon dungeon, Position position) {
		super(dungeon, "wire", position, false, EntityUpdateOrder.OTHER);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

	@Override
	public EntityResponse response() {
    	return new EntityResponse(getId(), getType() + redstoneComponent.getPower(), getPosition(), getInteractable());
    }
	
}
