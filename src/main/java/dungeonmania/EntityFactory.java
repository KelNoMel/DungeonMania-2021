package dungeonmania;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.components.CollectableState;
import dungeonmania.entities.BattleResolver;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Bow;
import dungeonmania.entities.buildable.Shield;
import dungeonmania.entities.collectables.Armour;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.collectables.HealthPotion;
import dungeonmania.entities.collectables.InvincibilityPotion;
import dungeonmania.entities.collectables.InvisibilityPotion;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.collectables.rare.TheOneRing;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.entities.spawners.MercenarySpawner;
import dungeonmania.entities.spawners.SpiderSpawner;
import dungeonmania.entities.spawners.ZombieToastSpawner;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Door;
import dungeonmania.entities.statics.Exit;
import dungeonmania.entities.statics.FloorSwitch;
import dungeonmania.entities.statics.Portal;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Position;

public class EntityFactory {
	
	public static EntityList loadEntities(JSONArray entityArray, Dungeon loadingDungeon) throws JSONException {
    	
    	EntityList newEntities = new EntityList();
    	
    	int numEntities = entityArray.length();
    	for (int i = 0; i < numEntities; i++) {
    		Entity newEntity = constructEntity(entityArray.getJSONObject(i), loadingDungeon);
    		if (newEntity != null) {
    			newEntities.add(newEntity);
        		if (newEntity instanceof Player) {
        			Collections.swap(newEntities, 0, newEntities.indexOf(newEntity));
        		}
    		}
    	}
    	
    	return newEntities;
    }
    
    /**
     * Used to construct specific entities given their JSON representation
     * @param entData
     * @return
     * @throws Exception
     */
	public static Entity constructEntity(JSONObject entData, Dungeon loadingDungeon) {
		Position pos = new Position(entData.getInt("x"), entData.getInt("y"));
		
		int topLayer = 3;
		int movingLayer = 2;
		int itemLayer = 1;
		int bottomLayer = 0;
		
		String entityType = entData.getString("type");
		
		entData.remove("type");
		entData.remove("x");
		entData.remove("y");
		
		switch (entityType) {
			case "player":
				return new Player(loadingDungeon, pos.asLayer(topLayer), entData);
			// Statics
			case "wall":
				return new Wall(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "exit":
				return new Exit(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "boulder":
				return new Boulder(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "switch":
				return new FloorSwitch(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "door":
				return new Door(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "portal":
				return new Portal(loadingDungeon, pos.asLayer(bottomLayer), entData);
			
			// Moving
			case "spider":
				return new Spider(loadingDungeon, pos.asLayer(movingLayer), entData);
			case "zombie_toast":
				return new ZombieToast(loadingDungeon, pos.asLayer(movingLayer), entData);
			case "mercenary":
				return new Mercenary(loadingDungeon, pos.asLayer(movingLayer), entData);
				
			// Collectable
			case "treasure":
				return new Treasure(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "key":
				return new Key(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "health_potion":
				return new HealthPotion(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "invincibility_potion":
				return new InvincibilityPotion(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "invisibility_potion":
				return new InvisibilityPotion(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "wood":
				return new Wood(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "arrow":
				return new Arrow(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "bomb":
				return new Bomb(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "sword":
				return new Sword(loadingDungeon, pos.asLayer(itemLayer), CollectableState.MAP, entData);
			case "armour":
				return new Armour(loadingDungeon, pos.asLayer(itemLayer), CollectableState.MAP, entData);
				
			// Rare Collectable
			case "the_one_ring":
				return new TheOneRing(loadingDungeon, pos.asLayer(itemLayer), entData);
				
			/// Buildable
			case "bow":
				Bow bow = new Bow(loadingDungeon, pos.asLayer(itemLayer), CollectableState.INVENTORY, entData);
				return bow;
			case "shield":
				Shield shield = new Shield(loadingDungeon, pos.asLayer(itemLayer), CollectableState.INVENTORY, entData);
				return shield;
			
			// Non spec-defined
			case "mercenary_spawner":
				return new MercenarySpawner(loadingDungeon, pos, 20, entData);
			case "spider_spawner":
				// TODO load spawner info from save
				return new SpiderSpawner(loadingDungeon, pos, 10, entData);
			case "zombie_toast_spawner":
				// TODO load spawner info from save
				return new ZombieToastSpawner(loadingDungeon, pos, 10, entData);
			
			case "battle_resolver":
				return new BattleResolver(loadingDungeon, pos, entData);
			
				// Type is not correct or has not been implemented
			default:
				System.out.println(entityType + " could not be loaded");
				return null;
		}
	}
}
