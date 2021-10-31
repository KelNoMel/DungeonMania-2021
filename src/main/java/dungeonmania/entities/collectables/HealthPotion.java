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

public class HealthPotion extends Entity {

	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	private ConsumableComponent consumableComp = new ConsumableComponent(this, 2, 1, 1);
	
	public HealthPotion(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "health_potion", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (getDungeon().getPlayer().getUsedList().containsKey(getId())) {
			
			// Effects of potion: Restore health
			getDungeon().getPlayer().setHealth(10);
		}
	}

	protected void updateEntity() {
		
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
