package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.entities.redstone.RedstoneComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class Bomb extends Entity {


	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	// Durability is 999 so entity doesn't die when placed
	private ConsumableComponent consumableComp = new ConsumableComponent(this, 2, 999, 1);
	public RedstoneComponent redstoneComponent = new RedstoneComponent(this, 3);


	public Bomb(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "bomb", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
	}

	// When bomb is used, it is placed on the same position as the player
	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (getDungeon().getPlayer().getUsedList().containsKey(getId())) {
			
			// Effects of bomb: Bomb is placed on the current tile the player is on
			collectableComp.place();
		}
	}

	// Check if any adjacent positions contain a triggering entity and explode if that's the case
	protected void updateEntity() {
		// Explodes if adjacent to an activated object
		if (redstoneComponent.isActivated()) {
			explode();
		}
	}

	// Sets all entities within a radius of 3 to entitystate.dead
	public void explode() {
		// Cannot explode in inventory, (pretty sure collected bombs in inventory still retain their original position)
		if (this.collectableComp.getCollectableState() == CollectableState.INVENTORY) {
			return;
		}

		for (Entity e : getDungeon().getEntitiesInRadius(getPosition(), 1.5)) {
			// Removes all entities except player within a radius of 3
			if (e.getType() != "player") {
				e.setState(EntityState.DEAD);
			}
		}
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
