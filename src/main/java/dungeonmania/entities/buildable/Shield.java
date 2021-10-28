package dungeonmania.entities.buildable;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Shield extends Entity {

	// durability for the number of times a shield can take a hit

	// decrease the effect of enemy attacks by 2 damage
	
	public Shield(Dungeon dungeon, Position position) {
		super(dungeon, "shield", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		
	}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

}
