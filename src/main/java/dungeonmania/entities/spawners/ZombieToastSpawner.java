package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.GameMode;
import dungeonmania.util.Position;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;

public class ZombieToastSpawner extends Spawner {

	static private final int maxZombies = 3;
	
	public ZombieToastSpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "zombie_toast_spawner", position, 20, entitySpecificData);
		toggleDisplay(true);
	}

	public void spawnEntity() {
		Dungeon d = getDungeon();
		if (d.getGameMode() != GameMode.PEACEFUL && 
				d.getEntities().numEntitiesOfType(Spider.class) < maxZombies) {
			new ZombieToast(getDungeon(), getPosition().asLayer(EntityFactory.movingLayer), new JSONObject());
		}
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
