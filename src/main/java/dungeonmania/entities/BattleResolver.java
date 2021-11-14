package dungeonmania.entities;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.BattleItemComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.WeaponComponent;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.entities.collectables.rare.TheOneRing;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.entities.collectables.Armour;
import dungeonmania.entities.collectables.rare.Anduril;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Position;

public class BattleResolver extends Entity {
	private final Double allySupportRange = 2.0;
	private final Double mercFrenzyRange = 2.0;

	public BattleResolver(Dungeon dungeon, Position position) {
		super(dungeon, "battle_resolver", position, false, EntityUpdateOrder.BATTLERESOLVER);
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
		String playerStatus = d.getPlayer().getStatus();

		// the bad guys at the player's position
		List<Entity> battleEnemies = getEnemiesToBattle(player);

		// Potion effects can prematurely end the player battle with different outcomes
		switch(playerStatus) {
			// Continue as normal towards battle
			case "normal":
				break;
			// Immediately stop the battle for the player, noone takes damage
			case "invisible":
				return;
			// Kill all the combating enemies automatically and end the battle
			case "invincible":
				// Mercenaries still frenzy	
				if (battleEnemies.size() > 0) {
					frenzyMercanaries(player);
				}
				for (Entity enemy : battleEnemies) {
					enemy.setState(EntityState.DEAD);
					rewardItems(enemy);
				}
				return;
		}

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

		if (battleEnemies.size() > 0) {
			// this should only be called if the player goes into a battle
			frenzyMercanaries(player);
		}

		// Battle time!
		for (Entity enemy : battleEnemies) {
			while (true) {
				BattleComponent enemyBattleState = enemy.getComponent(BattleComponent.class);

				// if either fighter died in a previous encounter skip this battle
				if (!enemyBattleState.isAlive()) {
					// Defeated enemies may drop items before ending the battle
					rewardItems(enemy);
					break;
				}
				if (!playerBattleState.isAlive()) break;
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
					if (isAnduril(singleAttacks.get(0).getEntity())) {
						if (attackWithAnduril(enemyBattleState, playersDamage, true)) {
							// weapon durabability--;
							singleAttacks.get(0).useItem();
						}
					} else {
						if (attackFighter(enemyBattleState, playersDamage)) {
							// weapon durabability--;
							singleAttacks.get(0).useItem();
						}
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
				if (getDungeon().getGamemode().asString().equals("peaceful")) {
					continue;
				} else {
					attackFighter(playerBattleState, enemysDamage);
				}
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

	private void rewardItems(Entity enemy) {
		Random random = new Random();
		Dungeon d = getDungeon();
		Player player = d.getPlayer();
		
		// Mercenaries have a 1/5 chance to drop armour, random durability with a max of 10
		if (random.nextInt(100) % 5 == 0 && enemy.getType().equals("mercenary")) {
			Armour armour = new Armour(getDungeon(), player.getPosition(), CollectableState.INVENTORY, random.nextInt(10));
				d.transferToInventory(armour);
		}
		
		// Chances of getting a rare item 1/10, subject to change
		if (random.nextInt(100) % 10 == 0) {
			// The two rare items have an equal chance to be spawned
			// A bit brittle, but OK since only two rares
			if (random.nextInt(100) % 2 == 0) {
				TheOneRing ring = new TheOneRing(getDungeon(), player.getPosition());
				ring.setCollectableState(CollectableState.INVENTORY);
				d.transferToInventory(ring);
			} else {
				Anduril anduril = new Anduril(getDungeon(), player.getPosition());

				anduril.setCollectableState(CollectableState.INVENTORY);
				d.transferToInventory(anduril);
			}
		}
	}

	public static boolean isPlayer(Entity e) {
		return e instanceof	Player;
	}

	public static boolean isEnemy(Entity e) {
		if (
			(e instanceof Mercenary && !((Mercenary)e).aiComponent.getAIState().getName().equals("MercAlly")) 
			|| e instanceof Spider 
			|| e instanceof ZombieToast
			|| e instanceof Hydra
		) {
			return true;
		}
		return false;
	}

	public static boolean isAlly(Entity e) {
		if (
			(e instanceof Mercenary && ((Mercenary)e).aiComponent.getAIState().getName().equals("MercAlly")) 
		) {
			return true;
		}
		return false;
	}

	public static boolean isHydra(Entity e) {
		return e.getType().equals("hydra");
	}

	public static boolean isAnduril(Entity e) {
		return e.getType().equals("anduril");
	}

	private List<Entity> getEnemiesToBattle(Entity entity) {
		return getDungeon().getEntitiesAtPosition(entity.getPosition()).stream()
			.filter(e -> isEnemy(e))
			.collect(Collectors.toList());
	}

	private boolean attackFighter(BattleComponent fighterBattleState, int damage) {
		return attackWithAnduril(fighterBattleState, damage, false);
	}

	private boolean attackWithAnduril(BattleComponent fighterBattleState, int damage, boolean isAnduril) {
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
		Random roll = new Random();
		if (isHydra(fighter) && !isAnduril) {
			if (roll.nextInt(100) % 2 == 0) {
				fighterBattleState.dealDamage(damage);
			} else {
				fighterBattleState.heal(damage);
			}
		} else {
			fighterBattleState.dealDamage(damage);
		}
		
		// If fighter is zero health, kill entity
		if (fighterBattleState.getHealth() <= 0) {
			fighterBattleState.getEntity().setState(EntityState.DEAD);
			// If fighter is player, try to revive
			if (isPlayer(fighter)) {
				TheOneRing ring = fighter.getDungeon().getPlayer().retrieveTypeFromInventory(TheOneRing.class);
				if (ring != null) {
					ring.revive();
				}
				
			}
		}
		
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

	/**
	 * Set all mercanaries within range into the FRENZY movement state for the 
	 * next tick
	 * @param player
	 */
	private void frenzyMercanaries(Player player) {
		Dungeon dungeon = getDungeon();
		List<Mercenary> mercs = dungeon.getEntities().getEntitiesByType(Mercenary.class)
			.stream().map(e -> (Mercenary)e).collect(Collectors.toList());
		for (Mercenary merc : mercs) {
			if (Position.withinRange(player.getPosition(), merc.getPosition(), mercFrenzyRange)) {
				// set to frenzy state
				merc.getComponent(MoveComponent.class).setType(MovementType.FRENZY);
			}
		}
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
