package dungeonmania.components.aistates;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Direction;

public class AIMercAttack extends AIState {

	private Mercenary merc;
	
	public AIMercAttack(AIComponent owner, Mercenary m) {
		super(owner);
		this.merc = m;
	}
	
	public void processInput(InputState inputState) {
		// Garbage brain pathfinding - move in the same direction as the player
		// Update this for actual pathfinding like A* or somen
		Direction mercMoveDirection = inputState.getMovementDirection();
		// Actual pathfinding should also work with the following line???
		// merc.getDungeon().getPlayer().getPosition();
		merc.moveComponent.setMoveDirection(mercMoveDirection);
	}

	public void updateState() {}

	public void onEnter() {}

	public void onExit() {}

	public String getName() {
		return "MercAttack";
	}
}
