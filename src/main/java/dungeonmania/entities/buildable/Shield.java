package dungeonmania.entities.buildable;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.BattleItemComponent;

public class Shield extends Buildable {
	final private int totalDurability = 5;
	final private int armour = 2;
	
	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public ArmourComponent armourComponent;

	// if a buildable is found on the map
	public Shield(Dungeon dungeon, Position position)  {
		super(dungeon,  BuildableEnum.SHIELD.getType(), position, false, BuildableEnum.SHIELD.getRecipe());
		collectableComponent = new CollectableComponent(this, 1, CollectableState.INVENTORY);
		battleItemComponent = new BattleItemComponent(this, 2, totalDurability);
		armourComponent = new ArmourComponent(this, 3, armour);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
