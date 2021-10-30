package dungeonmania.entities;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Dungeon.Bounds;
import dungeonmania.entities.moving.Spider;
import dungeonmania.util.Position;

public class SpiderSpawner extends Spawner {

	static private final Random randomiser = new Random();
	static private final int maxSpiders = 3;
	
	public SpiderSpawner(Dungeon dungeon, Position position, int tickSpawnRate) {
		super(dungeon, position, tickSpawnRate);
	}

	public void spawnEntity() {
		if (getDungeon().numEntitiesOfType(Spider.class) < maxSpiders) {
			Bounds b = getDungeon().getBounds();
			int xPos = generateRandom(b.getMinBounds().getX(), b.getMaxBounds().getX());
			int yPos = generateRandom(b.getMinBounds().getY(), b.getMaxBounds().getY());
			new Spider(getDungeon(), new Position(xPos, yPos));			
		}
	}

	private static int generateRandom(int low, int high) {
		return randomiser.nextInt(high-low) + low;
	}
}