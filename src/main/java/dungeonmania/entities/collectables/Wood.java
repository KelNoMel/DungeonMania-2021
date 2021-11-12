package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;

import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Wood extends Entity {
	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);

	public Wood(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "wood", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
