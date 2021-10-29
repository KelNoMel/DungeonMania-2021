package dungeonmania.components;

import java.util.List;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveComponent extends Component {

	public Direction moveDirection = null;
	public MovementType movementType;
	
	public MoveComponent(Entity owningEntity, int updateOrder, MovementType movementType) {
		super(owningEntity, updateOrder);
		this.movementType = movementType;
	}

	public void processInput(InputState inputState) {
		moveDirection = inputState.getMovementDirection();
	}

	public void updateComponent() {
		if (moveDirection == null) return;
		
		// Portal so needs to happen in processInput?????/
		Entity parentEntity = getEntity();
		Position newPosition = parentEntity.getPosition();
		switch (movementType) {
			case GHOST:
				// Phase through all objects
				newPosition = moveGhost(parentEntity); break;
			case NORMAL:
				// Move as normal
				newPosition = moveNormal(parentEntity); break;
		}
		
		parentEntity.setPosition(newPosition);
	}
	
	private Position moveGhost(Entity entityToMove) {
		return entityToMove.getPosition().translateBy(moveDirection);
	}
	
	private Position moveNormal(Entity entityToMove) {
		Position moveLocation = moveGhost(entityToMove);
		
		List<Entity> moveEntities = entityToMove.getDungeon().getEntitiesAtPosition(moveLocation);
		
		// Attempt to move if boulder in move location
		boolean boulderMoved = true;
		boolean isWall = false;
		for (Entity e : moveEntities) {
			if (e instanceof Boulder) {				
				boulderMoved = ((Boulder)e).moveBoulder(moveDirection);
				break;
			} else if (e instanceof Wall) {
				isWall = true;
				break;
			}
		}
		
		// Instead for boulder a collisionless raycast could be passed down the checking chain?
		
		// Only move if move space is not covered by a wall or an unmovable boulder
		if (!(isWall) && boulderMoved) {
			return moveLocation;
		}
		return entityToMove.getPosition();
	}

	public void setMoveDirection(Direction moveDirection) {
		this.moveDirection = moveDirection;
	}
}
