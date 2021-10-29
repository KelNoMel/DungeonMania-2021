package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectibleComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.util.Position;

public class HealthPotion extends Entity {

	public HealthPotion(Dungeon dungeon, Position position) {
		super(dungeon, "health_potion", position, false);
		addComponent(new CollectibleComponent(this, 1));
	}

	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (dungeon.getPlayer().getUsedList().containsKey(getId())) {
			dungeon.getPlayer().setHealth(10);
			setState(EntityState.DEAD);
		}
	}

	protected void updateEntity() {
	}

}
