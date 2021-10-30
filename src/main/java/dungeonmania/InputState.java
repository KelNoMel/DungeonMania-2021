package dungeonmania;

import dungeonmania.util.Direction;

public class InputState {
	private String itemUsed;
	private Direction movementDirection;
	private String interactId;
	
	public InputState(String itemUsed, Direction movementDirection, String interactId) {
		this.itemUsed = itemUsed;
		this.movementDirection = movementDirection;
		this.interactId = interactId;
	}
	
	public String getItemUsed() {
		return itemUsed;
	}
	
	public Direction getMovementDirection() {
		return movementDirection;
	}
	
	public String getInteractId() {
		return interactId;
	}
}
