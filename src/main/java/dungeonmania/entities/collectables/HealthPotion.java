package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class HealthPotion extends Entity {

	public HealthPotion(Dungeon dungeon, Position position) {
		super(dungeon, "health_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {
		// Check if a health potion was already used in this tick
		boolean alreadyUsed = dungeon.getDeadInventory().contains("health_potion");
		
		if (inputState.getItemUsed() == "health_potion" && alreadyUsed == false) {
			dungeon.getPlayer().setHealth(10);
			this.state == EntityState.DEAD;
		}
	}

	protected void updateEntity() {
		if (dungeon.isPlayerHere(position))
	}

}
