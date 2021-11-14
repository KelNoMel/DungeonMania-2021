package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Arrow extends Entity {
	public Arrow(Dungeon dungeon, Position position) {
		super(dungeon, "arrow", position, false, EntityUpdateOrder.OTHER);
		new CollectableComponent(this, 1, CollectableState.MAP);
		new ConsumableComponent(this, 1, 1, 1);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}
	
	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
