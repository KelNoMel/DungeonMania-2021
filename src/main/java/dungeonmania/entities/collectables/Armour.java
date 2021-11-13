package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.BattleItemComponent;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Armour extends Entity {
	final private int totalDurability = 6;
	final private int armour = 4;

	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public ArmourComponent armourComponent;
	
	public Armour(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon, "armour", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		battleItemComponent = new BattleItemComponent(this, 2, totalDurability);
		armourComponent = new ArmourComponent(this, 3, armour);
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
