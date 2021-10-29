package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIMercAttack;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Mercenary extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	
	public Mercenary(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary", position, true);
		aiComponent.registerState(new AIMercAttack(aiComponent, this));
		aiComponent.changeState("MercAttack");
	}

	protected void inputEntity(InputState inputState) {
		moveComponent.setMoveDirection(inputState.getMovementDirection());
	}

	protected void updateEntity() {
		
	}
}
