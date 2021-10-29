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
		// Check if item was queued to be used
		if (dungeon.getPlayer.getUsedList.containsKey(this.id)) {
			dungeon.getPlayer().setHealth(10);
			this.state == EntityState.DEAD;
		}
	}

	protected void updateEntity() {
	}

}
