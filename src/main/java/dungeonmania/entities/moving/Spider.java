package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Spider extends Entity {

	AIComponent aiComponent = new AIComponent(this, 1);
	MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.GHOST);
	
	public Spider(Dungeon dungeon, Position position) {
		super(dungeon, "spider", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
