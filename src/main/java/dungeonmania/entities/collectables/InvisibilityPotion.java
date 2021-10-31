package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ConsumableComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Entity {

	public InvisibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invisibility_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (getDungeon().getPlayer().getUsedList().containsKey(getId())) {
			
			// Effects of potion: Make all enemies go into afraid AI state
			// Afraid AI: Run away from player, battle auto-resolves to win
			getDungeon().getPlayer().addComponent(EffectComponent(EffectComponent(getDungeon().getPlayer(), 3));
			getDungeon().getPlayer().setStatus("invincible");
		}
	}
	
	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
