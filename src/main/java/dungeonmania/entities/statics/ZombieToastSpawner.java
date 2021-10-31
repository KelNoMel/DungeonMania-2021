package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity {

	public ZombieToastSpawner(Dungeon dungeon, Position position) {
		super(dungeon, "spawner", position, true);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	
}
