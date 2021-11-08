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
import dungeonmania.components.BattleItemComponent;
import dungeonmania.components.Component;
import dungeonmania.components.WeaponComponent;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Position;

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
		List<BattleItemComponent> playerBattleItems = player.getInventory().stream()
			.filter(e -> e.getComponent(BattleItemComponent.class) != null)
			.map(e -> (BattleItemComponent)e.getComponent(BattleItemComponent.class))
			.collect(Collectors.toList());
		List<WeaponComponent> playerWeapons = playerBattleItems.stream()
			.filter(e -> e.getEntity().getComponent(WeaponComponent.class) != null)
			.map(e -> (WeaponComponent)e.getEntity().getComponent(WeaponComponent.class))
			.collect(Collectors.toList());
		List<ArmourComponent> playerArmour = playerBattleItems.stream()
			.filter(e -> e.getEntity().getComponent(ArmourComponent.class) != null)
			.map(e -> (ArmourComponent)e.getEntity().getComponent(ArmourComponent.class))
			.collect(Collectors.toList());

		// the good guys
		List<Mercenary> battleAllies = d.getEntities().stream()
			.filter(e -> isAlly(e))
			.map(e -> (Mercenary)e)
			.collect(Collectors.toList());
		
		// the bad guys at the player's position
		List<Entity> battleEnemies = d.getEntitiesAtPosition(player.getPosition()).stream()
			.filter(e->isEnemy(e))
			.collect(Collectors.toList());

		// for player battle enemies using ally support

		// Battle time!
		for (Entity enemy : battleEnemies) {
			
			while (true) {
				BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);
				String preAttackHealth = playerBattleState.getHealthAsString();
				String enemyPreAttackHealth = enemyBattleState.getHealthAsString();

				
				int currArmour = 0;
				for (ArmourComponent armour : playerArmour) {
					if (armour.isBroken()) continue;
					currArmour += armour.getArmour();
					armour.useItem();
				}

				playerBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / (10 + currArmour));
				
				ArrayList<WeaponComponent> singleAttacks = new ArrayList<>();
				ArrayList<WeaponComponent> extraAttacks = new ArrayList<>();

				// use highest damage single attack and all extra attacks
				for (WeaponComponent weapon : playerWeapons) {
					if (weapon.isBroken()) continue;
					if (weapon.getType().equals(AttackTypeEnum.EXTRA)) {
						extraAttacks.add(weapon);
					} else if (singleAttacks.size() == 0) {
						singleAttacks.add(weapon);
					} else if (weapon.getDamage() > singleAttacks.get(0).getDamage()) {
						singleAttacks.add(weapon);
						singleAttacks.remove(0);
					}
				}
				battleAnimation(d, player, playerBattleState, preAttackHealth);

				// use only one single turn weapon or fist fight
				if (singleAttacks.size() == 0) {
					// fist fight
					enemyBattleState.dealDamage(playerBattleState.getScaledAttackDamage() / 5);
					battleAnimation(d, enemy, enemyBattleState, enemyPreAttackHealth);
				} else {
					// player uses weapon
					enemyBattleState.dealDamage(playerBattleState.getScaledAttackDamage(singleAttacks.get(0).getDamage()) / 5);
					singleAttacks.get(0).useItem();
					battleAnimation(d, enemy, enemyBattleState, enemyPreAttackHealth);
				}

				// use extra weapons
				for (WeaponComponent extraWeapon : extraAttacks) {
					if (!enemyBattleState.isAlive()) break;
					
					enemyBattleState.dealDamage(playerBattleState.getScaledAttackDamage(extraWeapon.getDamage()) / 5);
					battleAnimation(d, enemy, enemyBattleState, enemyPreAttackHealth);
					extraWeapon.useItem();

				}

				// allies fight
				for (Mercenary ally : battleAllies) {
					if (!enemyBattleState.isAlive()) break;
					
					BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);
					String allyPreAttackHealth = allyBattleState.getHealthAsString();
					if (!allyBattleState.isAlive()) break;
					if (!enemyBattleState.isAlive()) break;

					allyBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / 10);
					enemyBattleState.dealDamage(allyBattleState.getScaledAttackDamage() / 5);
					battleAnimation(d, enemy, enemyBattleState, enemyPreAttackHealth);
					battleAnimation(d, ally, allyBattleState, allyPreAttackHealth);
				}

				if (!enemyBattleState.isAlive()) break;
			}
		}
		
		
		for (Mercenary ally : battleAllies) {
			// the bad guys at the player's position
			battleEnemies = d.getEntitiesAtPosition(ally.getPosition()).stream()
				.filter(e->isEnemy(e))
				.collect(Collectors.toList());
			BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);

			// for all allies battle enemies
			// Battle time!
			for (Entity enemy : battleEnemies) {
				while (true) {
					BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);
					String allyPreAttackHealth = allyBattleState.getHealthAsString();
					String enemyPreAttackHealth = enemyBattleState.getHealthAsString();

					System.out.println("Ally health was: " + allyPreAttackHealth);
					System.out.println("Enemy health was: " + enemyPreAttackHealth);


					if (!allyBattleState.isAlive()) break;
					if (!enemyBattleState.isAlive()) break;
					
					enemyBattleState.dealDamage(allyBattleState.getScaledAttackDamage() / 5);
					if (!enemyBattleState.isAlive()) break;
					allyBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / 10);
					
					battleAnimation(d, ally, allyBattleState, allyPreAttackHealth);
					battleAnimation(d, enemy, enemyBattleState, enemyPreAttackHealth);

					allyPreAttackHealth = allyBattleState.getHealthAsString();
					enemyPreAttackHealth = enemyBattleState.getHealthAsString();

					System.out.println("Ally health is now: " + allyPreAttackHealth);
					System.out.println("Enemy health is now: " + enemyPreAttackHealth);


					if (!allyBattleState.isAlive()) break;
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

	public static void battleAnimation(Dungeon dungeon, Entity fighter, BattleComponent battleComponent, String preAttackHealth) {
		dungeon.queueAnimation(
			new AnimationQueue(
				"PostTick",
				fighter.getId(),
				Arrays.asList(
					"healthbar set " + preAttackHealth,
					"healthbar tint 0x00ff00",
					"healthbar set " + battleComponent.getHealthAsString() + ", over 1.5s",
					"healthbar tint 0xff0000, over 0.5s"
				),
				false,
				-1
			)
		);
		dungeon.queueAnimation(
			new AnimationQueue(
				"PostTick",
				fighter.getId(),
				Arrays.asList("healthbar shake, over 0.5s, ease Sin"),	// TODO: ease in?
				false, 
				0.5
			)
		);
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
