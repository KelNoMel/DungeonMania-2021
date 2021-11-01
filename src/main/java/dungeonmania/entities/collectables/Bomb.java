package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Bomb extends Entity {


	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	private ConsumableComponent consumableComp = new ConsumableComponent(this, 2, 1, 1);

	public Bomb(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "bomb", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {
		// Check if item was queued to be used
		if (getDungeon().getPlayer().getUsedList().containsKey(getId())) {
			
			// Effects of bomb: Bomb is placed on the current tile the player is on
		}
	}

	protected void updateEntity() {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
