package dungeonmania.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.InputState;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.Component;
import dungeonmania.components.WeaponComponent;
import dungeonmania.components.ArmourComponent;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Position;
import dungeonmania.entities.collectables.BattleItem;

public class BattleResolver extends Entity {

	public BattleResolver(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "battle_resolver", position, false, entitySpecificData);
		toggleDisplay(false);
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {
		Dungeon d = getDungeon();
		
		// the player
		Player player = d.getPlayer();
		BattleComponent playerBattleState = player.getComponent(BattleComponent.class);

		// items used in battle
		List<BattleItem> playerBattleItems = player.getInventory().stream().filter(e -> e instanceof BattleItem).map(e -> (BattleItem)e).collect(Collectors.toList());
		List<BattleItem> playerWeapons = playerBattleItems.stream().filter(e -> e.getComponent(WeaponComponent.class) != null).collect(Collectors.toList());
		List<BattleItem> playerArmour = playerBattleItems.stream().filter(e -> e.getComponent(ArmourComponent.class) != null).collect(Collectors.toList());

		// the good guys
		List<Mercenary> battleAllies = d.getEntities().stream().filter(e -> isAlly(e)).map(e -> (Mercenary)e).collect(Collectors.toList());
		
		// the bad guys at the player's position
		List<Entity> battleEnemies = d.getEntitiesAtPosition(player.getPosition()).stream().filter(e->isEnemy(e)).collect(Collectors.toList());

		// for player battle enemies using ally support

		// Battle time!
		for (Entity enemy : battleEnemies) {
			
			while (true) {
				BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);
				
				String preAttackHealth = playerBattleState.getHealthAsString();
				
				int currArmour = 0;
				for (BattleItem armour : playerArmour) {
					currArmour += armour.getComponent(ArmourComponent.class).getArmour();
					armour.useItem();
				}

				playerBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / 10 + currArmour);
				if (!playerBattleState.isAlive()) break;
				
				// player animation
				d.queueAnimation(
					new AnimationQueue(
						"PostTick",
						player.getId(),
						Arrays.asList(
							"healthbar set " + preAttackHealth,
							"healthbar tint 0x00ff00",
							"healthbar set " + playerBattleState.getHealthAsString() + ", over 1.5s",
							"healthbar tint 0xff0000, over 0.5s"
				        ),
						false,
						-1
					)
				);
				// also the player animation?
				d.queueAnimation(
					new AnimationQueue(
						"PostTick",
						player.getId(),
						Arrays.asList("healthbar shake, over 0.5s, ease Sin"),	// TODO: ease in?
						false, 
						0.5
					)
				);
				
				int currDamage = 0;
				for (BattleItem weapon : playerWeapons) {
					// TODO: if we use a weapon
					// if () {

					// }
					currDamage += weapon.getComponent(ArmourComponent.class).getArmour();

					weapon.useItem();
					enemyBattleState.dealDamage(playerBattleState.getScaledAttackDamage() / 5);	// TODO: add player's weapons
					if (!enemyBattleState.isAlive()) break;
				}

				for (Mercenary ally : battleAllies) {
					BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);
					enemyBattleState.dealDamage(allyBattleState.getScaledAttackDamage() / 5);	// TODO: add player's weapons
					if (!enemyBattleState.isAlive()) break;
				}

				
			}
		}
		
		
		for (Mercenary ally : battleAllies) {
			// the bad guys at the player's position
			battleEnemies = d.getEntitiesAtPosition(ally.getPosition()).stream().filter(e->isEnemy(e)).collect(Collectors.toList());

			// for all allies battle enemies
			// Battle time!
			for (Entity enemy : battleEnemies) {
				BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);
				while (true) {
					BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);
					String preAttackHealth = allyBattleState.getHealthAsString();
					
					allyBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / 10); // TODO: add player's armour
					if (!allyBattleState.isAlive()) break;
					
					// player animation
					// d.queueAnimation(
					// 	new AnimationQueue(
					// 		"PostTick",
					// 		player.getId(),
					// 		Arrays.asList(
					// 			"healthbar set " + preAttackHealth,
					// 			"healthbar tint 0x00ff00",
					// 			"healthbar set " + playerBattleState.getHealthAsString() + ", over 1.5s",
					// 			"healthbar tint 0xff0000, over 0.5s"
					// 		),
					// 		false,
					// 		-1
					// 	)
					// );
					// // also the player animation?
					// d.queueAnimation(
					// 	new AnimationQueue(
					// 		"PostTick",
					// 		player.getId(),
					// 		Arrays.asList("healthbar shake, over 0.5s, ease Sin"),	// TODO: ease in?
					// 		false, 
					// 		0.5
					// 	)
					// );
					
					enemyBattleState.dealDamage(allyBattleState.getScaledAttackDamage() / 5);
					if (!enemyBattleState.isAlive()) break;
				}
			}
		}
	}

	
	private boolean isEnemy(Entity e) {
		if (
			(e instanceof Mercenary && !((Mercenary)e).aiComponent.getAISate().getName().equals("MercAlly")) 
			|| e instanceof Spider 
			|| e instanceof ZombieToast
		) {
			return true;
		}
		return false;
	}

	private boolean isAlly(Entity e) {
		if (
			(e instanceof Mercenary && ((Mercenary)e).aiComponent.getAISate().getName().equals("MercAlly")) 
		) {
			return true;
		}
		return false;
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
