package dungeonmania.entities.buildable;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;

/**
 * Bow Entity can shoot from a range
 */
public class Bow extends Buildable {
	public Bow(Dungeon dungeon, Position position) {
		super(dungeon,  BuildableEnum.BOW.getType(), position, false, 
			BuildableEnum.BOW.getRecipes());
	}
			
	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
