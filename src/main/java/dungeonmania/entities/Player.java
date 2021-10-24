package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	private int health = 10;
	private int attackDamage = 10;

	public Player(Dungeon dungeon, Position position) {
		super(dungeon, "player", position, false);
	}
	
	protected void inputEntity(InputState inputState) {
		ArrayList<Entity> closeEntities = dungeon.getEntitiesInRadius(getPosition(), 1);

		Position moveLocation = getPosition().translateBy(inputState.getMovementDirection());
		Entity moveEntity = null;
		for (Entity e : closeEntities) {
			if (e.getPosition().equals(moveLocation)) {
				moveEntity = e;
				break;
			}
		}
		
		if (moveEntity == null || !(moveEntity instanceof Wall)) {
			setPosition(getPosition().translateBy(inputState.getMovementDirection()));			
		}	
	}

	protected void updateEntity() {}
}
