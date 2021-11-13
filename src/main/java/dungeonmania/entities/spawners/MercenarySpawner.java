package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.GameMode;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class MercenarySpawner extends Spawner {

	static private final int maxMercenaries = 2;
	
	public MercenarySpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "mercenary_spawner", position, 40, entitySpecificData);
	}

	public void spawnEntity() {
		if (getDungeon().getGameMode() != GameMode.PEACEFUL && 
				getDungeon().getEntities().numEntitiesOfType(Mercenary.class) < maxMercenaries) {
			new Mercenary(getDungeon(), getPosition().asLayer(EntityFactory.movingLayer), new JSONObject());
		}
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
