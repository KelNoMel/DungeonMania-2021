package dungeonmania.entities.buildable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;

// debugging
import dungeonmania.entities.buildable.Bow;


public class Shield extends Buildable {
	final private int totalDurability = 5;
	final private int armour = 2;
	
	public CollectableComponent collectableComponent;

	// if a buildable is found on the map
	public Shield(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon,  BuildableEnum.SHIELD.getType(), position, false, BuildableEnum.SHIELD.getRecipes(), entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
