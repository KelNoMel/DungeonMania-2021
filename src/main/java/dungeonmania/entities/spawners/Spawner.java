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
	
	public Spawner(Dungeon dungeon, String type, Position position, int tickSpawnRate) {
		super(dungeon, type, position, true, EntityUpdateOrder.SPAWNER);
		toggleDisplay(false);
		this.tickSpawnRate = tickSpawnRate;
		ticksUntilNextSpawn = tickSpawnRate;
	}

	public void inputEntity(InputState inputState) {}
	
	public void updateEntity() {
		ticksUntilNextSpawn--;
		if (ticksUntilNextSpawn <= 0) {
			if (spawnEntity()) ticksUntilNextSpawn = tickSpawnRate;
		}
	}
		
	public void saveJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("spawnRate", tickSpawnRate);
		baseJSON.put("nextSpawn", ticksUntilNextSpawn);
	}
	
	protected void loadJSONEntitySpecific(JSONObject entityData) {
		if (entityData.has("spawnRate")) {
			tickSpawnRate = entityData.getInt("spawnRate");
		}
		if (entityData.has("nextSpawn")) {
			ticksUntilNextSpawn = entityData.getInt("nextSpawn");
		}
	}

	public void changeSpawnRate(int newSpawnRate) {
		tickSpawnRate = newSpawnRate;
		ticksUntilNextSpawn = newSpawnRate;
	}
	
	public abstract boolean spawnEntity();
	
	public int getSpawnRate() { return tickSpawnRate; }
	public void setSpawnRate(int tickSpawnRate) { this.tickSpawnRate = tickSpawnRate; }
}
