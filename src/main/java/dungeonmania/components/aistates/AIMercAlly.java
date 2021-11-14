package dungeonmania.components.aistates;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Dijkstras;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AIMercAlly extends AIState {

	private Mercenary merc;
	
	public AIMercAlly(AIComponent owner, Mercenary m) {
		super(owner);
		this.merc = m;
	}
	
	// public void processInput(InputState inputState) {
	// 	// Garbage brain pathfinding - move in the same direction as the player
	// 	// Update this for actual pathfinding like A* or somen
	// 	Direction mercMoveDirection = inputState.getMovementDirection();
	// 	// Actual pathfinding should also work with the following line???
	// 	Player p = merc.getDungeon().getPlayer();
	// 	Position mercToPlayer = Position.calculatePositionBetween(p.getPosition(), merc.getPosition());
	// 	// Move to right if on top of player
	// 	if (merc.withinRange(p, 0)) {
	// 		mercMoveDirection = Direction.RIGHT;
	// 	} else if (mercToPlayer.getY() > 0) {
	// 		mercMoveDirection = Direction.UP;
	// 	} else if (mercToPlayer.getX() > 0) {
	// 		mercMoveDirection = Direction.LEFT;
	// 	} else if (mercToPlayer.getY() < 0) {
	// 		mercMoveDirection = Direction.DOWN;
	// 	} else if (mercToPlayer.getX() < 0) {
	// 		mercMoveDirection = Direction.RIGHT;
	// 	}
		
	// 	merc.moveComponent.setMoveDirection(mercMoveDirection);
	// }

	public void processInput(InputState inputState) {
		Direction mercMoveDirection = inputState.getMovementDirection();
		// Actual pathfinding should also work with the following line???
		Player p = merc.getDungeon().getPlayer();
		Dijkstras shortestPath = new Dijkstras(merc.getDungeon(), merc);
		Position nextPos = shortestPath.getNextPosition(merc.getPosition(), p.getPosition());
		Position mercToNext = Position.calculatePositionBetween(nextPos, merc.getPosition());
		// Move to right if on top of player
		if (merc.getPosition().equals(nextPos)) {
			mercMoveDirection = Direction.RIGHT;
		} else if (mercToNext.getY() > 0) {
			mercMoveDirection = Direction.UP;
		} else if (mercToNext.getX() > 0) {
			mercMoveDirection = Direction.LEFT;
		} else if (mercToNext.getY() < 0) {
			mercMoveDirection = Direction.DOWN;
		} else if (mercToNext.getX() < 0) {
			mercMoveDirection = Direction.RIGHT;
		}
		
		merc.moveComponent.setMoveDirection(mercMoveDirection);
	}

	public void updateState() {
		//System.out.println("Ally!");
	}

	public void onEnter() {}

	public void onExit() {}

	public String getName() {
		return "MercAlly";
	}
}
