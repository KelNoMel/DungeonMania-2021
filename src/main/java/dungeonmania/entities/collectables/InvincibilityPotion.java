package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Entity {

	public InvincibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invincibility_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {
		// Check if a health potion was already used in this tick
		boolean alreadyUsed = getDungeon().getPlayer().getUsedList().containsKey("invincibility_potion");
		
		// Would "consumable" be a valid component?
		if (inputState.getItemUsed() == "invincibility_potion" && alreadyUsed == false) {
			getDungeon().getPlayer().setHealth(10);
			
			setState(EntityState.DEAD);

			getDungeon().getPlayer().getUsedList().put("invincibility_potion", null);
		}
	}
	
	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
