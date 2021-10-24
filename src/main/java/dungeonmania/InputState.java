package dungeonmania;

import dungeonmania.util.Direction;

public class InputState {
	private String itemUsed;
	private Direction movementDirection;
	
	public InputState(String itemUsed, Direction movementDirection) {
		this.itemUsed = itemUsed;
		this.movementDirection = movementDirection;
	}
	
	public String getItemUsed() {
		return itemUsed;
	}
	
	public Direction getMovementDirection() {
		return movementDirection;
	}
}
