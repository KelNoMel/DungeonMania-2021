package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.battles.AttackTypeEnum;
import dungeonmania.components.battles.BattleComponent;
import dungeonmania.components.battles.Power;
import dungeonmania.components.battles.PowerUser;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIZombieHostile;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {
	final int maxHealth = 4;
	final int damage = 5;

	public BattleComponent battleComponent = new BattleComponent(this, 1, 
		new Power(maxHealth, maxHealth, damage, 0, PowerUser.ENEMY, AttackTypeEnum.FISTS));
	public AIComponent aiComponent = new AIComponent(this, 100);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	
	public ZombieToast(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "zombie", position, false, entitySpecificData);
		aiComponent.registerState(new AIZombieHostile(aiComponent, this));
		aiComponent.changeState("ZombieHostile");
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
