package dungeonmania.entities.collectables.rare;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.components.BattleItemComponent;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.WeaponComponent;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Anduril extends Entity {
	int totalDurability = Integer.MAX_VALUE;
	int damage = 50;
	
	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	public BattleItemComponent battleItemComponent = new BattleItemComponent(this, 2, totalDurability);
	public WeaponComponent weaponComponent = new WeaponComponent(this, 3, damage, AttackTypeEnum.SINGLE);

	public Anduril(Dungeon dungeon, Position position) {
		super(dungeon, "anduril", position, false, EntityUpdateOrder.OTHER);
	}

	protected void updateEntity() {}
	protected void inputEntity(InputState inputState) {}
	
	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
	public void setCollectableState(CollectableState state) {
		collectableComp.setCollectableState(state);
	}	
}
