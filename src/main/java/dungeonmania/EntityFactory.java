package dungeonmania;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.components.CollectableState;
import dungeonmania.entities.*;
import dungeonmania.entities.bosses.Assassin;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.entities.buildable.*;
import dungeonmania.entities.collectables.*;
import dungeonmania.entities.collectables.rare.*;
import dungeonmania.entities.moving.*;
import dungeonmania.entities.redstone.*;
import dungeonmania.entities.spawners.*;
import dungeonmania.entities.statics.*;
import dungeonmania.util.Position;

public class EntityFactory {
	
	public static final int topLayer = 3;
	public static final int movingLayer = 2;
	public static final int itemLayer = 1;
	public static final int bottomLayer = 0;
	
	public static void loadEntities(JSONArray entityArray, Dungeon loadingDungeon, EntityList loadList) throws JSONException {
    	
		boolean inventory = (loadingDungeon.getEntities() != loadList);
		
    	int numEntities = entityArray.length();
    	for (int i = 0; i < numEntities; i++) {
    		JSONObject loadJSON = entityArray.getJSONObject(i);
    		Entity constructedEntity = constructEntity(loadJSON, loadingDungeon);
    		if (constructedEntity != null) {
    			constructedEntity.loadJSON(loadJSON);    			
    		}
    		
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
		
		String entityType = entData.getString("type");
		
		switch (entityType) {
			case "player":
				return new Player(loadingDungeon, pos.asLayer(topLayer));
			// Statics
			case "wall":
				return new Wall(loadingDungeon, pos.asLayer(bottomLayer));
			case "exit":
				return new Exit(loadingDungeon, pos.asLayer(bottomLayer));
			case "boulder":
				return new Boulder(loadingDungeon, pos.asLayer(itemLayer));
			case "switch":
				return new FloorSwitch(loadingDungeon, pos.asLayer(bottomLayer));
			case "door":
				return new Door(loadingDungeon, pos.asLayer(bottomLayer));
			case "portal":
				return new Portal(loadingDungeon, pos.asLayer(bottomLayer));
			
			// Moving
			case "spider":
				return new Spider(loadingDungeon, pos.asLayer(movingLayer));
			case "zombie_toast":
				return new ZombieToast(loadingDungeon, pos.asLayer(movingLayer));
			case "mercenary":
				return new Mercenary(loadingDungeon, pos.asLayer(movingLayer));
				
			// Collectable
			case "treasure":
				return new Treasure(loadingDungeon, pos.asLayer(itemLayer));
			case "key":
				return new Key(loadingDungeon, pos.asLayer(itemLayer));
			case "health_potion":
				return new HealthPotion(loadingDungeon, pos.asLayer(itemLayer));
			case "invincibility_potion":
				return new InvincibilityPotion(loadingDungeon, pos.asLayer(itemLayer));
			case "invisibility_potion":
				return new InvisibilityPotion(loadingDungeon, pos.asLayer(itemLayer));
			case "wood":
				return new Wood(loadingDungeon, pos.asLayer(itemLayer));
			case "arrow":
				return new Arrow(loadingDungeon, pos.asLayer(itemLayer));
			case "bomb":
				return new Bomb(loadingDungeon, pos.asLayer(itemLayer));
			case "sword":
				return new Sword(loadingDungeon, pos.asLayer(itemLayer), CollectableState.MAP);
			case "armour":
				return new Armour(loadingDungeon, pos.asLayer(itemLayer), CollectableState.MAP);
			case "sun_stone":
				return new SunStone(loadingDungeon, pos.asLayer(itemLayer));
				
			// Rare Collectable
			case "the_one_ring":
				return new TheOneRing(loadingDungeon, pos.asLayer(itemLayer));
			case "anduril":
				return new Anduril(loadingDungeon, pos.asLayer(itemLayer));
				
			/// Buildable
			case "bow":
				return new Bow(loadingDungeon);
			case "shield":
				return new Shield(loadingDungeon);
			case "sceptre":
				return new Sceptre(loadingDungeon);
			case "midnight_armour":
				return new MidnightArmour(loadingDungeon);

			// Redstone
			case "wire":
				return new Wire(loadingDungeon, pos.asLayer(bottomLayer));
			case "light_bulb_on":
				return new LightBulb(loadingDungeon, pos.asLayer(bottomLayer));
			case "light_bulb_off":
				return new LightBulb(loadingDungeon, pos.asLayer(bottomLayer));
			case "switch_door":
				return new SwitchDoor(loadingDungeon, pos.asLayer(bottomLayer));

			// Bosses
			case "assassin":
				return new Assassin(loadingDungeon, pos.asLayer(bottomLayer));
			case "hydra":
				return new Hydra(loadingDungeon, pos.asLayer(bottomLayer));
				
			// Non spec-defined
			case "mercenary_spawner":
				return new MercenarySpawner(loadingDungeon, pos);
			case "spider_spawner":
				return new SpiderSpawner(loadingDungeon, pos);
			case "hydra_spawner":
				return new HydraSpawner(loadingDungeon, pos);
			case "zombie_toast_spawner":
				return new ZombieToastSpawner(loadingDungeon, pos.asLayer(bottomLayer));
			
			case "battle_resolver":
				return new BattleResolver(loadingDungeon, pos);
			
			// Type is not correct or has not been implemented
			default:
				return null;
		}
	}
}
