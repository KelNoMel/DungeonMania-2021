package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.redstone.RedstoneComponent;
import dungeonmania.util.Position;

public class FloorSwitch extends Entity {
	
	public RedstoneComponent redstoneComponent = new RedstoneComponent(this, 1);
	private boolean isTriggered;
	
	public FloorSwitch(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "switch", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {
		boolean hasboulder = false;
		for (Entity entity : getDungeon().getEntitiesAtPosition(getPosition())) {
			if (entity instanceof Boulder) {
				isTriggered = true;
				redstoneComponent.powerOn();
				hasboulder = true;
			} 
		}
		if(!hasboulder) {
			isTriggered = false;
			redstoneComponent.powerOff();
		}
	}

	public boolean isTriggered() {
		return isTriggered;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
 