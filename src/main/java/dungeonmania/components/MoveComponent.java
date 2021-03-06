package dungeonmania.components;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.redstone.SwitchDoor;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
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

	public void processInput(InputState inputState) {}

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
			case FRENZY:
				// Move twice in one tick
				newPosition = moveNormal(parentEntity);
				parentEntity.setPosition(newPosition);
				newPosition = moveNormal(parentEntity);
				// frenzy does not last
				movementType = MovementType.NORMAL;
				break;
		}
		
		parentEntity.setPosition(newPosition);
	}
	
	public void saveJSONComponentSpecific(JSONObject entityJSON) {}
	public void loadJSONComponentSpecific(JSONObject entityData) {}
	
	// Entity moves ignoring blocking entities like walls or boulders
	private Position moveGhost(Entity entityToMove) {
		return entityToMove.getPosition().translateBy(moveDirection);
	}
	
	// Entity moves considering boulders, walls and other blockables
	private Position moveNormal(Entity entityToMove) {
		// Check if normal move is valid, with current direction
		Boolean permission = canIMove(entityToMove);
		
		// Only move if permission given
		if (permission) {
			return moveGhost(entityToMove);
		}
		return entityToMove.getPosition();
	}
	// Checks whether or not current set move is allowed
	public Boolean canIMove(Entity entityToMove) {
		Position moveLocation = moveGhost(entityToMove);

		// Spider can pretty much move anywhere, (boulder checking is in its' ai)
		if (entityToMove.getType().equals("spider")) {
			return true;
		}
		
		List<Entity> moveEntities = entityToMove.getDungeon().getEntitiesAtPosition(moveLocation);
		
		// Attempt to move if boulder in move location
		boolean boulderMoved = true;
		boolean isWall = false;
		boolean doorUnlocked = true;
		for (Entity e : moveEntities) {
			if (e instanceof Boulder) {				
				boulderMoved = ((Boulder)e).moveBoulder(moveDirection);
				break;
			} else if (e instanceof Wall) {
				isWall = true;
				break;
			} else if (e instanceof Door) {
				doorUnlocked = ((Door)e).attemptUnlock();
				break;
			} else if (e instanceof SwitchDoor) {
				doorUnlocked = ((SwitchDoor)e).isUnlocked();
				break;
			}
		}
		
		// Instead for boulder a collisionless raycast could be passed down the checking chain?
		
		// Only move if move space is not covered by a wall or an unmovable boulder
		if (!(isWall) && boulderMoved && doorUnlocked) {
			return true;
		}
		return false;
	}

	public void setMoveDirection(Direction moveDirection) {
		this.moveDirection = moveDirection;
	}

	public void setType(MovementType movementType) {
		this.movementType = movementType;
	}
}
