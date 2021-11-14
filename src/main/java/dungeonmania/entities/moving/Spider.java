package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIRunAway;
import dungeonmania.components.aistates.AISpiderHostile;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Spider extends Entity {
	final int maxHealth = 3;
	final int damage = 3;

	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.GHOST);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 5, 5);
	
	public Spider(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "spider", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
		aiComponent.registerState(new AISpiderHostile(aiComponent, this));
		aiComponent.registerState(new AIRunAway(aiComponent, this, moveComponent));
		aiComponent.changeState("SpiderHostile");
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {
		String playerState = getDungeon().getPlayer().getStatus();
		switch (playerState) {
			case "invincible":
				aiComponent.changeState("enemyRunAway");
				break;
			default:
			aiComponent.changeState("SpiderHostile");
			break;
		}
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
