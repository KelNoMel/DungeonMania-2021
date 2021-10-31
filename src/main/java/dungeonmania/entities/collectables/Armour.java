package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.battles.BattleItemComponent;
import dungeonmania.components.battles.ArmourComponent;

public class Armour extends Entity {
	final private int totalDurability = 6;
	final private int armour = 4;

	public ArmourComponent armourComponent;
	public CollectableComponent collectableComponent;
	
	public Armour(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon, "armour", position, false, entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		armourComponent = new ArmourComponent(this, 2, totalDurability, armour);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}



}
