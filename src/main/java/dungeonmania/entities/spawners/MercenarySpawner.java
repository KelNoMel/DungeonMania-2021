package dungeonmania.entities.spawners;

import dungeonmania.Dungeon;
import dungeonmania.entities.BattleResolver;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bosses.Assassin;
import dungeonmania.EntityFactory;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class MercenarySpawner extends Spawner {

	static private final int maxMercenaries = 2;
	
	public MercenarySpawner(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary_spawner", position, 40);
	}

	public boolean spawnEntity() {
		Dungeon d = getDungeon();
		
		// Only spawn when at least one enemy
		// No enemies?
		boolean noEnemies = true;
		for (Entity e : d.getEntities()) {
			if (BattleResolver.isEnemy(e)) {
				noEnemies = false;
			}
		}
		if (noEnemies) return false;
		
		if (d.getEntities().numEntitiesOfType(Mercenary.class) < maxMercenaries) {
			Position spawnLayer = getPosition().asLayer(EntityFactory.movingLayer);

			int percent = (int) Math.ceil(Math.random() * 100);
			if (percent <= 30) {
				new Assassin(d, spawnLayer);
			} else {
				new Mercenary(d, spawnLayer);
			}
			return true;
		}
		return false;
	}
}
