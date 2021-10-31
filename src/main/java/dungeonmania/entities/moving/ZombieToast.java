package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {

	AIComponent aiComponent = new AIComponent(this, 100);
	
	public ZombieToast(Dungeon dungeon, Position position) {
		super(dungeon, "zombie", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
