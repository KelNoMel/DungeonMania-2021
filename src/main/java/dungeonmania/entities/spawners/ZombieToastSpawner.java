package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;
import dungeonmania.entities.moving.ZombieToast;

public class ZombieToastSpawner extends Spawner {

	public ZombieToastSpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "zombie_toast_spawner", position, 10, entitySpecificData);
		toggleDisplay(true);
	}

	public void spawnEntity() {
		new ZombieToast(getDungeon(), getPosition(), new JSONObject());
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
