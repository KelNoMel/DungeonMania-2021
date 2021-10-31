package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.moving.ZombieToast;

public class ZombieToastSpawner extends Spawner {

	public ZombieToastSpawner(Dungeon dungeon, Position position, int tickSpawnRate, JSONObject entitySpecificData) {
		super(dungeon, "zombie_spawner", position, tickSpawnRate, entitySpecificData);
	}

	public void spawnEntity() {
		new ZombieToast(getDungeon(), getPosition(), new JSONObject());
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
