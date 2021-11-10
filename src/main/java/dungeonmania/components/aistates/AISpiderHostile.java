package dungeonmania.components.aistates;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.moving.Spider;
import dungeonmania.util.Direction;

public class AISpiderHostile extends AIState {

	private Spider spider;
	
	private int cyclePos = -1;
	
	public AISpiderHostile(AIComponent owner, Spider spider) {
		super(owner);
		this.spider = spider;
	}
	
	public void processInput(InputState inputState) {
		Direction spiderMoveDirection = Direction.UP;
		
		if (cyclePos != -1) {
			// Cycle!
			if (cyclePos == 0 || cyclePos == 7) {
				spiderMoveDirection = Direction.RIGHT;
			} else if (cyclePos == 1 || cyclePos == 2) {
				spiderMoveDirection = Direction.DOWN;
			} else if (cyclePos == 3 || cyclePos == 4) {
				spiderMoveDirection = Direction.LEFT;
			} else if (cyclePos == 5 || cyclePos == 6) {
				spiderMoveDirection = Direction.UP;
			}
		}
	
		cyclePos++;
		if (cyclePos >= 8) {
			cyclePos = 0;
		}
		spider.moveComponent.setMoveDirection(spiderMoveDirection);
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
