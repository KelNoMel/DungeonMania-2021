package dungeonmania.entities.buildable;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.components.CollectableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.BattleItemComponent;

public class MidnightArmour extends Buildable {
	final private int totalDurability = 5;  // TODO: make this infinite by adjusting ArmourComponent
	final private int armour = 25;
	
	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public ArmourComponent armourComponent;
	public BattleComponent battleComponent;

	public MidnightArmour(Dungeon dungeon, Position position, JSONObject entitySpecificData)  {
		super(dungeon,  BuildableEnum.MIDNIGHT_ARMOUR.getType(), position, false, BuildableEnum.MIDNIGHT_ARMOUR.getRecipe(), entitySpecificData);
		battleItemComponent = new BattleItemComponent(this, 1, totalDurability);
		armourComponent = new ArmourComponent(this, 2, armour);
		battleComponent = new BattleComponent(this, 3, 30, 10);
	}

	protected void inputEntity(InputState inputState) {
		Dungeon d = getDungeon();
		for (Entity e : d.getEntities()) {
			if (e instanceof ZombieToast) { return; }
		}
	}

	protected void updateEntity() {
		battleComponent.setAttackDamage(15);
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
