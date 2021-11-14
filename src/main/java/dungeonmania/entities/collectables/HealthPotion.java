package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class HealthPotion extends Entity {

	public HealthPotion(Dungeon dungeon, Position position) {
		super(dungeon, "health_potion", position, false, EntityUpdateOrder.OTHER);
		new CollectableComponent(this, 1, CollectableState.MAP);
		new ConsumableComponent(this, 2, 1, 1);
	}

	// When health potion gets used, player restores health
	protected void inputEntity(InputState inputState) {
		Player player = getDungeon().getPlayer();
		// Check if item was queued to be used
		if (player.getUsedList().containsKey(getId())) {
			
			// Effects of potion: Restore health
			player.setHealth(100);
		}
	}

	protected void updateEntity() {}

	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
