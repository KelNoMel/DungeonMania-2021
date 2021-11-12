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
	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);

	public Anduril(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "anduril", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void setCollectableState(CollectableState state) {
		collectableComp.setCollectableState(state);
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
