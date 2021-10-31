package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIMercAlly;
import dungeonmania.components.aistates.AIMercHostile;
import dungeonmania.components.battles.AttackTypeEnum;
import dungeonmania.components.battles.BattleComponent;
import dungeonmania.components.battles.Power;
import dungeonmania.components.battles.PowerUser;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Mercenary extends Entity {
	final int maxHealth = 10;
	final int damage = 12;

	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);

	BattleComponent battleComponent = new BattleComponent(this, 1, 
		new Power(maxHealth, maxHealth, damage, 0, PowerUser.ENEMY, AttackTypeEnum.FISTS));
	
	public Mercenary(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary", position, true);
		aiComponent.registerState(new AIMercHostile(aiComponent, this));
		aiComponent.registerState(new AIMercAlly(aiComponent, this));
		aiComponent.changeState("MercHostile");
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {}
}
