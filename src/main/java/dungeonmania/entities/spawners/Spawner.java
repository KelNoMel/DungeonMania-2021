package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public abstract class Spawner extends Entity {

	private int tickSpawnRate;
	private int ticksUntilNextSpawn;
	
	public Spawner(Dungeon dungeon, String type, Position position, int tickSpawnRate, JSONObject entitySpecificData) {
		super(dungeon, type, position, true, EntityUpdateOrder.SPAWNER, entitySpecificData);
		toggleDisplay(false);
		this.tickSpawnRate = tickSpawnRate;
		ticksUntilNextSpawn = tickSpawnRate;
	}

	public void inputEntity(InputState inputState) {}
	
	public abstract void spawnEntity();
	public void updateEntity() {
		ticksUntilNextSpawn--;
		if (ticksUntilNextSpawn <= 0) {
			spawnEntity();
			ticksUntilNextSpawn = tickSpawnRate;
		}
	}
	
	public int getSpawnRate() { return tickSpawnRate; }
	public void setSpawnRate(int tickSpawnRate) { this.tickSpawnRate = tickSpawnRate; }

}
