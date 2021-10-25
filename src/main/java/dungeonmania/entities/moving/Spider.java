package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Spider extends Entity {

	AIComponent aiComponent = new AIComponent(this, 100, null, null);
	
	public Spider(Dungeon dungeon, Position position) {
		super(dungeon, "spider", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
