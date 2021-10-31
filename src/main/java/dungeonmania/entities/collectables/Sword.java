package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.battles.AttackItemComponent;
import dungeonmania.components.battles.NormalAttack;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Sword extends Entity {

	final private int totalDurability = 7;
	final private int damage = 7;

	public AttackItemComponent attackItemComponent;
	public CollectableComponent collectableComponent;

	public Sword(Dungeon dungeon, Position position, CollectableState collectableState) {
		super(dungeon, "sword", position, false);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		attackItemComponent = new AttackItemComponent(this, 2, totalDurability, damage, new NormalAttack());
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
