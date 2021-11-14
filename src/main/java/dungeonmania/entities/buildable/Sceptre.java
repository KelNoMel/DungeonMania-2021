package dungeonmania.entities.buildable;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;

public class Sceptre extends Buildable {
	public static final int MINDCONTROL_TIME = 10;

	// if a buildable is found on the map
	public Sceptre(Dungeon dungeon, Position position)  {
		super(dungeon,  BuildableEnum.SCEPTRE.getType(), position, false, BuildableEnum.SCEPTRE.getRecipe());
	}
	
	public Sceptre(Dungeon dungeon) {
		super(dungeon, "sceptre", new Position(0,0), true);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	public void saveJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}

