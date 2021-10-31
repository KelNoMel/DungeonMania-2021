package dungeonmania.entities.spawners;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public abstract class Spawner extends Entity {

	private int tickSpawnRate;
	private int ticksUntilNextSpawn;
	
	public Spawner(Dungeon dungeon, Position position, int tickSpawnRate) {
		super(dungeon, "blank", position, false);
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
