package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Entity {

	public InvisibilityPotion(Dungeon dungeon, Position position) {
		super(dungeon, "invisibility_potion", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	
}
