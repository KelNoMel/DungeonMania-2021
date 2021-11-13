package dungeonmania.components.aistates;

import java.util.List;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AISpiderHostile extends AIState {

	private Spider spider;
	
	private int cyclePos = 0;
	private boolean initialSpawn = true;
	private int movementDirection = 1;
	
	public AISpiderHostile(AIComponent owner, Spider spider) {
		super(owner);
		this.spider = spider;
	}

	public void processInput(InputState inputState) {
		Direction spiderMoveDirection = Direction.UP;
		
		// Sort out initial behaviour
		if (initialSpawn) {
			initialSpawn = false;
			spider.moveComponent.setMoveDirection(spiderMoveDirection);
			return;
		}
		
		
		if (movementDirection == 1) {
			// Cycle clockwise!
			if      (cyclePos == 0 || cyclePos == 7) spiderMoveDirection = Direction.RIGHT;
			else if (cyclePos == 1 || cyclePos == 2) spiderMoveDirection = Direction.DOWN;
			else if (cyclePos == 3 || cyclePos == 4) spiderMoveDirection = Direction.LEFT;
			else if (cyclePos == 5 || cyclePos == 6) spiderMoveDirection = Direction.UP;
		} else {
			// Cycle opposite!
			if      (cyclePos == 0 || cyclePos == 1) spiderMoveDirection = Direction.LEFT;
			else if (cyclePos == 2 || cyclePos == 3) spiderMoveDirection = Direction.UP;
			else if (cyclePos == 4 || cyclePos == 5) spiderMoveDirection = Direction.RIGHT;
			else if (cyclePos == 6 || cyclePos == 7) spiderMoveDirection = Direction.DOWN;
		}
		// Check whether a boulder is in the spiders way and reverse direction/don't move if so
		Position newPos = spider.getPosition().translateBy(spiderMoveDirection);
		
		if (isBoulderAtPos(newPos)) {
			// Don't move this round, and reverse movement direction
			spiderMoveDirection = Direction.NONE;
			movementDirection = -movementDirection;				
		} else {
			cyclePos += (1*movementDirection);
			if (cyclePos >= 8) {
				cyclePos = 0;
			} else if (cyclePos < 0) {
				cyclePos = 7;
			}
		}		
		spider.moveComponent.setMoveDirection(spiderMoveDirection);
	}
	
	private boolean isBoulderAtPos(Position p) {
		List<Entity> entitiesAtPos = spider.getDungeon().getEntitiesAtPosition(p);
		
		for (Entity e : entitiesAtPos) {
			if (e instanceof Boulder) {
				return true;
			}
		}
		return false;
	}

	public void updateState() {
		// System.out.println("Spider Hostile");
	}

	public void onEnter() {}

	public void onExit() {}

	public String getName() {
		return "SpiderHostile";
	}
}
