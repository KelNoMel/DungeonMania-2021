package dungeonmania.entities.collectables.rare;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Anduril extends Entity {

	public Anduril(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "the_one_ring", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
