package dungeonmania.entities.moving;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class MercenarySpawner extends Entity {

	private static MercenarySpawner mercSpawner;
	
	
	// Must be >= 0 !
	private static final int tickSpawnRate = 10;
	
	private int ticksUntilNextSpawn = tickSpawnRate;
	private Position spawnPosition;
	
	public static MercenarySpawner createMercSpawner(Dungeon d, Position spawnPosition) {
		if (mercSpawner == null) {
			mercSpawner = new MercenarySpawner(d, spawnPosition);
		}
		return mercSpawner;
	}
	
	private MercenarySpawner(Dungeon dungeon, Position spawnPosition) {
		super(dungeon, "mercenary_spawner", spawnPosition, false);
		this.spawnPosition = spawnPosition;
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		ticksUntilNextSpawn--;
		if (ticksUntilNextSpawn <= 0) {
			new Mercenary(getDungeon(), spawnPosition);
			ticksUntilNextSpawn = tickSpawnRate;
		}
	}

}
