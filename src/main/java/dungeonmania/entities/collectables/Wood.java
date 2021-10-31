package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Wood extends Entity {

	public Wood(Dungeon dungeon, Position position) {
		super(dungeon, "wood", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
