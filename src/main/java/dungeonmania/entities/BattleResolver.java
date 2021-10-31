package dungeonmania.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.Component;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.util.Position;

public class BattleResolver extends Entity {

	public BattleResolver(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "battle_resolver", position, false, entitySpecificData);
		toggleDisplay(false);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {
		Dungeon d = getDungeon();
		
		Player player = d.getPlayer();
		BattleComponent playerBattleState = getBattleComponent(player);
		
		List<Entity> battleEnemies = d.getEntitiesAtPosition(player.getPosition());
		battleEnemies = battleEnemies.stream().filter(e->isEnemy(e)).collect(Collectors.toList());
		
		// Battle time!
		for (Entity e : battleEnemies) {
			BattleComponent enemyBattleState = getBattleComponent(e);
			
			playerBattleState.dealDamage(enemyBattleState.getScaledAttackDamage() / 10);
			if (!playerBattleState.isAlive()) break;
			
			enemyBattleState.dealDamage(playerBattleState.getScaledAttackDamage() / 5);
			if (!playerBattleState.isAlive()) break;
		}
	}
	
	private boolean isEnemy(Entity e) {
		if (e instanceof Mercenary || e instanceof Spider || e instanceof ZombieToast) {
			return true;
		}
		return false;
	}
	
	private BattleComponent getBattleComponent(Entity e) {
		List<Component> comps = e.getComponents();
		for (Component c : comps) {
			if (c instanceof BattleComponent) {
				return ((BattleComponent)c);
			}
		}
		// Shouldn't get here...
		return null;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
