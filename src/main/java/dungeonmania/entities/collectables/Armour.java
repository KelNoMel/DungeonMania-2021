package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Armour extends Entity {
	final private int totalDurability = 6;
	final private int armour = 4;

	public CollectableComponent collectableComponent;
	
	public Armour(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon, "armour", position, false, entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
	}

	// Armour already gets added into inventory by collectableComponent
	// Its' effects are seen in battlecomponent
	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}



}
