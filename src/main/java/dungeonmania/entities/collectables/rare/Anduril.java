package dungeonmania.entities.collectables.rare;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.util.Position;

public class Anduril extends Entity {
	private CollectableComponent collectableComp;

	public Anduril(Dungeon dungeon, Position position, CollectableState itemState, JSONObject entitySpecificData) {
		super(dungeon, "the_one_ring", position, false, entitySpecificData);
		collectableComp = new CollectableComponent(this, 1, itemState);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
