package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.GameMode;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class MercenarySpawner extends Spawner {

	static private final int maxMercenaries = 2;
	
	public MercenarySpawner(Dungeon dungeon, Position position, int tickSpawnRate, JSONObject entitySpecificData) {
		super(dungeon, "mercenary_spawner", position, tickSpawnRate, entitySpecificData);
	}

	public void spawnEntity() {
		if (getDungeon().getGameMode() != GameMode.PEACEFUL && 
				getDungeon().getEntities().numEntitiesOfType(Mercenary.class) < maxMercenaries) {
			new Mercenary(getDungeon(), getPosition(), new JSONObject());
		}
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
