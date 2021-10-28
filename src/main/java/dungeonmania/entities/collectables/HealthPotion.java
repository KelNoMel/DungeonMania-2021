package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class HealthPotion extends Entity {

	public HealthPotion(Dungeon dungeon, Position position) {
		super(dungeon, "health_potion", position, false);
		this.components.add(new CollectableComponent(this, 1, dungeon));
	}

	protected void inputEntity(InputState inputState) {
		// Check if a health potion was already used in this tick
		boolean alreadyUsed = dungeon.getPlayer().getUsedList().contains("health_potion");
		
		// Would "consumable" be a valid component?
		if (inputState.getItemUsed() == "health_potion" && alreadyUsed == false) {
			dungeon.getPlayer().setHealth(10);
			this.state == EntityState.DEAD;

			dungeon.getPlayer().getUsedList().add("health_potion");
		}
	}

	protected void updateEntity() {
	}

}
