package dungeonmania.entities.buildable;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.WeaponComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.components.BattleItemComponent;

public class MidnightArmour extends Buildable {
	final private int totalDurability = 5;  // TODO: make this infinite by adjusting ArmourComponent
	final private int armour = 25;
	final private int damage = 10;
	
	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public ArmourComponent armourComponent;
	public WeaponComponent weaponComponent;

	public MidnightArmour(Dungeon dungeon, Position position)  {
		super(dungeon,  BuildableEnum.MIDNIGHT_ARMOUR.getType(), position, false, BuildableEnum.MIDNIGHT_ARMOUR.getRecipe());
		battleItemComponent = new BattleItemComponent(this, 1, totalDurability);
		armourComponent = new ArmourComponent(this, 2, armour);
		weaponComponent = new WeaponComponent(this, 3, damage, AttackTypeEnum.EXTRA);
	}

	protected void inputEntity(InputState inputState) {
		Dungeon d = getDungeon();
		for (Entity e : d.getEntities()) {
			if (e instanceof ZombieToast) { return; }
		}
	}

	protected void updateEntity() {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
