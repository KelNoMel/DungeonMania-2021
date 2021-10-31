package dungeonmania.entities.collectables.rare;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class TheOneRing extends Entity {

	public TheOneRing(Dungeon dungeon, Position position) {
		super(dungeon, "the_one_ring", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
