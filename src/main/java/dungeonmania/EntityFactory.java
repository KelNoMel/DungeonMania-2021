package dungeonmania;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.components.CollectableState;
import dungeonmania.entities.*;
import dungeonmania.entities.buildable.*;
import dungeonmania.entities.collectables.*;
import dungeonmania.entities.collectables.rare.*;
import dungeonmania.entities.moving.*;
import dungeonmania.entities.redstone.*;
import dungeonmania.entities.spawners.*;
import dungeonmania.entities.statics.*;
import dungeonmania.util.Position;

public class EntityFactory {
	
	public static void loadEntities(JSONArray entityArray, Dungeon loadingDungeon, EntityList loadList) throws JSONException {
    	
		boolean inventory = (loadingDungeon.getEntities() != loadList);
		
    	int numEntities = entityArray.length();
    	for (int i = 0; i < numEntities; i++) {
    		Entity constructedEntity = constructEntity(entityArray.getJSONObject(i), loadingDungeon);
    		if (inventory) loadingDungeon.transferToInventory(constructedEntity);
    	}
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
				return new Boulder(loadingDungeon, pos.asLayer(itemLayer), entData);
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
			case "sun_stone":
				return new SunStone(loadingDungeon, pos.asLayer(itemLayer), entData);
				
			// Rare Collectable
			case "the_one_ring":
				return new TheOneRing(loadingDungeon, pos.asLayer(itemLayer), entData);
				
			/// Buildable
			case "bow":
				return new Bow(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "shield":
				return new Shield(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "sceptre":
				return new Sceptre(loadingDungeon, pos.asLayer(itemLayer), entData);
			case "midnight_armour":
				return new MidnightArmour(loadingDungeon, pos.asLayer(itemLayer), entData);

			// Redstone
			case "wire":
				return new Wire(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "light_bulb_on":
				return new LightBulb(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "light_bulb_off":
				return new LightBulb(loadingDungeon, pos.asLayer(bottomLayer), entData);
			case "switch_door":
				return new SwitchDoor(loadingDungeon, pos.asLayer(bottomLayer), entData);
				
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
