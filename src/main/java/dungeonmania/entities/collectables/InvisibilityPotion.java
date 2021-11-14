package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.components.EffectComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Entity {

	public InvisibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invisibility_potion", position, false, EntityUpdateOrder.OTHER);
		new CollectableComponent(this, 1, CollectableState.MAP);
		new ConsumableComponent(this, 2, 1, 1);
	}

	// Player gets invisibility status, can override other effects
	protected void inputEntity(InputState inputState) {
		Player player = getDungeon().getPlayer();
		// Check if item was queued to be used
		if (player.getUsedList().containsKey(getId())) {
			
			// Effects of potion: Make all enemies go into afraid AI state
			// Afraid AI: Run away from player, battle auto-resolves to win
			new EffectComponent(getDungeon().getPlayer(), 3);
			player.setStatus("invisible");
		}
	}
	
	protected void updateEntity() {}

	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
