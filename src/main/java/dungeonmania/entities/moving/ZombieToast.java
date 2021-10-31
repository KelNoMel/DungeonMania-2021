package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.battles.AttackTypeEnum;
import dungeonmania.components.battles.BattleComponent;
import dungeonmania.components.battles.Power;
import dungeonmania.components.battles.PowerUser;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {
	final int maxHealth = 4;
	final int damage = 5;

	BattleComponent battleComponent = new BattleComponent(this, 1, 
		new Power(maxHealth, maxHealth, damage, 0, PowerUser.ENEMY, AttackTypeEnum.FISTS));
	AIComponent aiComponent = new AIComponent(this, 100);
	
	public ZombieToast(Dungeon dungeon, Position position) {
		super(dungeon, "zombie", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
