package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class MercenarySpawner extends Spawner {

	public MercenarySpawner(Dungeon dungeon, Position position, int tickSpawnRate) {
		super(dungeon, "mercenary_spawner", position, tickSpawnRate);
	}

	public void spawnEntity() {
		new Mercenary(getDungeon(), getPosition());
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
