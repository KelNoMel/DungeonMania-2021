package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.util.Position;

public class HealthPotion extends Entity {

	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	private ConsumableComponent consumableComp = new ConsumableComponent(this, 1, 1, 1);
	
	public HealthPotion(Dungeon dungeon, Position position) {
		super(dungeon, "health_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (getDungeon().getPlayer().getUsedList().containsKey(getId())) {
			getDungeon().getPlayer().setHealth(10);
			setState(EntityState.DEAD);
		}
	}

	protected void updateEntity() {
		
	}

}
