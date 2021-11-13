package dungeonmania.entities.spawners;

import java.util.Random;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.Gamemode;
import dungeonmania.Dungeon.Bounds;
import dungeonmania.entities.moving.Spider;
import dungeonmania.util.Position;

public class SpiderSpawner extends Spawner {

	static private final Random randomiser = new Random();
	static private final int maxSpiders = 4;
	
	public SpiderSpawner(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "spider_spawner", position, 30, entitySpecificData);
	}

	public void spawnEntity() {
		Dungeon d = getDungeon();
		if (d.getGamemode() != Gamemode.PEACEFUL && 
				d.getEntities().numEntitiesOfType(Spider.class) < maxSpiders) {
			
			Bounds b = d.getBounds();
			int xPos = generateRandom(b.getMinBounds().getX(), b.getMaxBounds().getX());
			int yPos = generateRandom(b.getMinBounds().getY(), b.getMaxBounds().getY());
			new Spider(getDungeon(), new Position(xPos, yPos).asLayer(EntityFactory.movingLayer), new JSONObject());
		}
	}

	private static int generateRandom(int low, int high) {
		if (low == high) return low;
		return randomiser.nextInt(high-low) + low;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}