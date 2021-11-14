package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.Gamemode;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.components.EffectComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Entity {

	public InvincibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invincibility_potion", position, false, EntityUpdateOrder.OTHER);
		new CollectableComponent(this, 1, CollectableState.MAP);
		new ConsumableComponent(this, 2, 1, 1);
	}

	// Player gets the invincible status, can override other effects
	protected void inputEntity(InputState inputState) {
		Player player = getDungeon().getPlayer();
		// Check if item was queued to be used
		if (player.getUsedList().containsKey(getId())) {
			if (getDungeon().getGamemode() == Gamemode.HARD) {				
				setState(EntityState.DEAD);
				return;
			}
			
			// Effects of potion: Make all enemies go into afraid AI state
			// Afraid AI: Run away from player, battle auto-resolves to win
			new EffectComponent(getDungeon().getPlayer(), 3);
			player.setStatus("invincible");
		}
	}
	
	protected void updateEntity() {}
	
	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
