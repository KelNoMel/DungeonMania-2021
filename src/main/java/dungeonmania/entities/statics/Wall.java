package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Wall extends Entity {

	public Wall(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "wall", position, false, entitySpecificData);
	}

	protected void updateEntity() {}

	protected void inputEntity(InputState inputState) {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
