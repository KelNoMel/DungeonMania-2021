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
	private final int allySupportRange = 2;

	public BattleResolver(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "battle_resolver", position, false, entitySpecificData);
		toggleDisplay(false);
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {
		Dungeon d = getDungeon();

		// the good guys
		List<Mercenary> battleAllies = d.getEntities().stream()
			.filter(e -> isAlly(e))
			.map(e -> (Mercenary)e)
			.collect(Collectors.toList());
		

		playerBattle(battleAllies);
		alliesBattle(battleAllies);
	}

	/**
	 * Player battles all enemies in its position with weapons and ally support
	 * Note: player receives damage at the end of each loop to allow for
	 * predictable damage when added with a weapon
	 * @param battleAllies
	 */
	private void playerBattle(List<Mercenary> battleAllies) {
		Dungeon d = getDungeon();
		
		// the player
		Player player = d.getPlayer();
		BattleComponent playerBattleState = player.getComponent(BattleComponent.class);

		// the bad guys at the player's position
		List<Entity> battleEnemies = getEnemiesToBattle(player);

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

		// Battle time!
		for (Entity enemy : battleEnemies) {
			while (true) {
				BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);

				// if either fighter died in a previous encounter skip this battle
				if (!playerBattleState.isAlive()) break;
				if (!enemyBattleState.isAlive()) break;

				// use armour
				int currArmour = 0;
				for (ArmourComponent armour : playerArmour) {
					if (armour.isBroken()) continue;
					currArmour += armour.getArmour();
					armour.useItem();
				}

				// get damage
				int playersDamage = playerBattleState.getScaledAttackDamage() / 5;
				int enemysDamage = enemyBattleState.getScaledAttackDamage() / (10 + currArmour);
				
				// get player's new weapon attacks this turn
				ArrayList<WeaponComponent> singleAttacks = new ArrayList<>();
				ArrayList<WeaponComponent> extraAttacks = new ArrayList<>();

				// use highest damage single attack and all extra attacks
				for (WeaponComponent weapon : playerWeapons) {
					// don't use broken weapons
					if (weapon.isBroken()) continue;

					if (weapon.getType().equals(AttackTypeEnum.EXTRA)) {
						// queue all extra attacks
						extraAttacks.add(weapon);
					} else if (singleAttacks.size() == 0) {
						// add single per round weapon to the queue over fists
						singleAttacks.add(weapon);
					} else if (weapon.getDamage() > singleAttacks.get(0).getDamage()) {
						// replace current single weapon with highest damage
						// weapon in the queue
						singleAttacks.add(weapon);
						singleAttacks.remove(0);
					}
				}

				// use only one single turn weapon or fist fight
				if (singleAttacks.size() == 0) {
					// fist fight
					attackFighter(enemyBattleState, playersDamage);
				} else {
					// player uses weapon on enemy
					playersDamage = playerBattleState.getScaledAttackDamage(singleAttacks.get(0).getDamage()) / 5;
					if (attackFighter(enemyBattleState, playersDamage)) {
						// weapon durabability--;
						singleAttacks.get(0).useItem();
					}
				}

				// use extra weapons
				for (WeaponComponent extraWeapon : extraAttacks) {
					// player uses weapon
					playersDamage = playerBattleState.getScaledAttackDamage(extraWeapon.getDamage()) / 5;
					if (attackFighter(enemyBattleState, playersDamage)) {
						// weapon durabability--;
						extraWeapon.useItem();
					}
				}

				// allies do additional attacks without getting hit
				for (Mercenary ally : battleAllies) {
					// check if ally is within battle range
					if (!Position.withinRange(ally.getPosition(), player.getPosition(), allySupportRange)) continue;
					BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);
					// if either fighter died in a previous encounter skip this battle
					if (!allyBattleState.isAlive()) break;
					int allysDamage = allyBattleState.getScaledAttackDamage() / 5;
					attackFighter(enemyBattleState, allysDamage);
				}

				// player gets attacked
				attackFighter(playerBattleState, enemysDamage);
			}
		}
	}

	private void alliesBattle(List<Mercenary> battleAllies) {
		for (Mercenary ally : battleAllies) {
			// the bad guys at the player's position
			BattleComponent allyBattleState = ally.getComponent(BattleComponent.class);
			List<Entity> battleEnemies = getEnemiesToBattle(ally);

			// for all allies battle enemies
			// Battle time!
			for (Entity enemy : battleEnemies) {
				while (true) {
					BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);
					
					// get damage
					int allysDamage = allyBattleState.getScaledAttackDamage() / 5;
					int enemysDamage = enemyBattleState.getScaledAttackDamage() / 10;

					// if either fighter died in a previous encounter skip this battle
					if (!allyBattleState.isAlive()) break;
					if (!enemyBattleState.isAlive()) break;

					attackFighter(enemyBattleState, allysDamage);
					attackFighter(allyBattleState, enemysDamage);
				}
			}
		}
	}

	public static boolean isPlayer(Entity e) {
		return e instanceof	Player;
	}

	public static boolean isEnemy(Entity e) {
		if (
			(e instanceof Mercenary && !((Mercenary)e).aiComponent.getAISate().getName().equals("MercAlly")) 
			|| e instanceof Spider 
			|| e instanceof ZombieToast
		) {
			return true;
		}
		return false;
	}

	public static boolean isAlly(Entity e) {
		if (
			(e instanceof Mercenary && ((Mercenary)e).aiComponent.getAISate().getName().equals("MercAlly")) 
		) {
			return true;
		}
		return false;
	}

	private List<Entity> getEnemiesToBattle(Entity entity) {
		return getDungeon().getEntitiesAtPosition(entity.getPosition()).stream()
			.filter(e -> isEnemy(e))
			.collect(Collectors.toList());
	}

	private boolean attackFighter(BattleComponent fighterBattleState, int damage) {
		// if this fighter died in a previous encounter skip this attack
		if (!fighterBattleState.isAlive()) return false;
		// pre-battle health
		String fighterPreAttackHealth = fighterBattleState.getHealthAsString();
		Entity fighter = fighterBattleState.getEntity();
		// pre-battle
		if (isPlayer(fighter)) {
			System.out.println("Player health was: " + fighterPreAttackHealth);
		} else if (isEnemy(fighter)) {
			System.out.println("Enemy health was: " + fighterPreAttackHealth);
		} else if (isAlly(fighter)) {
			System.out.println("Ally health was: " + fighterPreAttackHealth);
		}
		// fighter gets attacked
		fighterBattleState.dealDamage(damage);
		// animation
		battleAnimation(fighter, fighterBattleState, fighterPreAttackHealth);
		// post-battle
		if (isPlayer(fighter)) {
			System.out.println("Player health is now: " + fighterBattleState.getHealthAsString());
		} else if (isEnemy(fighter)) {
			System.out.println("Enemy health is now: " + fighterBattleState.getHealthAsString());
		} else if (isAlly(fighter)) {
			System.out.println("Ally health is now: " + fighterBattleState.getHealthAsString());
		}
		// weapon / other was successfully used
		return true;
	}

	public void battleAnimation(Entity fighter, BattleComponent battleComponent, String preAttackHealth) {
		Dungeon d = getDungeon();
		
		d.queueAnimation(
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
		d.queueAnimation(
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