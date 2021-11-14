package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.BattleResolver;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bosses.Assassin;
import dungeonmania.EntityFactory;
import dungeonmania.Gamemode;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class MercenarySpawner extends Spawner {

	static private final int maxMercenaries = 2;
	
	public MercenarySpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "mercenary_spawner", position, 40, entitySpecificData);
	}

	public void spawnEntity() {
		Dungeon d = getDungeon();
		
		// Only spawn when at least one enemy
		// No enemies?
		boolean noEnemies = true;
		for (Entity e : d.getEntities()) {
			if (BattleResolver.isEnemy(e)) {
				noEnemies = false;
			}
		}
		if (noEnemies) return;
		
		if (d.getEntities().numEntitiesOfType(Mercenary.class) < maxMercenaries) {
			Position spawnLayer = getPosition().asLayer(EntityFactory.movingLayer);

			int percent = (int) Math.ceil(Math.random() * 100);
			if (percent <= 30) {
				new Assassin(d, spawnLayer, new JSONObject());
			} else {
				new Mercenary(d, spawnLayer, new JSONObject());
			}
		}
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
