package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Mercenary extends Entity {

	AIComponent aiComponent = new AIComponent(this, 100, null, null);
	
	public Mercenary(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary", position, true);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
