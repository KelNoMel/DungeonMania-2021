package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Entity {

	public InvincibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invincibility_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
