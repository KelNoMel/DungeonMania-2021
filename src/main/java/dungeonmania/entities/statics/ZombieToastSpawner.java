package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity {
	
	public ZombieToastSpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "spawner", position, true, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

	@Override
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addJSONEntitySpecific(JSONObject baseJSON) {
		// TODO Auto-generated method stub
		
	}

}
