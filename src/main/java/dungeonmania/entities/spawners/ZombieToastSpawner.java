package dungeonmania.entities.spawners;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.Gamemode;
import dungeonmania.util.Position;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;

public class ZombieToastSpawner extends Spawner {

	static private final int maxZombies = 3;
	
	public ZombieToastSpawner(Dungeon dungeon, Position position) {
		super(dungeon, "zombie_toast_spawner", position, 20);
		toggleDisplay(true);
		if (dungeon.getGamemode() == Gamemode.HARD) changeSpawnRate(15);
	}

	public boolean spawnEntity() {
		Dungeon d = getDungeon();
		if (d.getGamemode() != Gamemode.PEACEFUL && 
				d.getEntities().numEntitiesOfType(Spider.class) < maxZombies) {
			new ZombieToast(getDungeon(), getPosition().asLayer(EntityFactory.movingLayer));
			return true;
		}
		return false;
	}
	
}
