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
import dungeonmania.components.aistates.AISpiderHostile;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Spider extends Entity {
	final int maxHealth = 3;
	final int damage = 3;

	public BattleComponent battleComponent = new BattleComponent(this, 1, 
		new Power(maxHealth, maxHealth, damage, 0, PowerUser.ENEMY, AttackTypeEnum.FISTS));
	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.GHOST);
	
	public Spider(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "spider", position, false, entitySpecificData);
		aiComponent.registerState(new AISpiderHostile(aiComponent, this));
		aiComponent.changeState("SpiderHostile");
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
