package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 100);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	
	public ZombieToast(Dungeon dungeon, Position position) {
		super(dungeon, "zombie", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
