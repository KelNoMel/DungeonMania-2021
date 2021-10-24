package dungeonmania.entities;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	private int health = 10;
	private int attackDamage = 10;

	public Player(Dungeon dungeon, Position position) {
		super(dungeon, "player", position, false);
	}

	protected void updateEntity() {}

	protected void inputEntity(InputState inputState) {
		setPosition(getPosition().translateBy(inputState.getMovementDirection()));
	}

}
