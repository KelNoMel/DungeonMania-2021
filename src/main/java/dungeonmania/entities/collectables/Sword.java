package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.WeaponComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;
import dungeonmania.components.BattleItemComponent;

public class Sword extends Entity {
	final private int totalDurability = 7;
	final private int damage = 7;

	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public WeaponComponent weaponComponent;

	public Sword(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon, "sword", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		battleItemComponent = new BattleItemComponent(this, 2, totalDurability);
		weaponComponent = new WeaponComponent(this, 3, damage, AttackTypeEnum.SINGLE);
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
