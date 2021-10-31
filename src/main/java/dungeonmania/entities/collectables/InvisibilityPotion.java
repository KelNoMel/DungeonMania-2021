package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.EffectComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Entity {

	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	
	public InvisibilityPotion(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "invisibility_potion", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {
		Player player = getDungeon().getPlayer();
		// Check if item was queued to be used
		if (player.getUsedList().containsKey(getId())) {
			
			// Effects of potion: Make all enemies go into afraid AI state
			// Afraid AI: Run away from player, battle auto-resolves to win
			player.addComponent(new EffectComponent(getDungeon().getPlayer(), 3));
			player.setStatus("invincible");
		}
	}
	
	protected void updateEntity() {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
