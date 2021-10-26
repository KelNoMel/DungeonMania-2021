package dungeonmania.entities.statics;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends Entity {

	public Boulder(Dungeon dungeon, Position position) {
		super(dungeon, "boulder", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public boolean moveBoulder(Direction d) {
		Position desiredMovePosition = getPosition().translateBy(d);
		
		List<Entity> moveEntities = dungeon.getEntitiesAtPosition(desiredMovePosition);
		
		for (Entity e : moveEntities) {
			if (e instanceof Boulder || e instanceof Wall) {
				return false;
			}
		}
		
		setPosition(desiredMovePosition);
		
		return true;
	}

}
