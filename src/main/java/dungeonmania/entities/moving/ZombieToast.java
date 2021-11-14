package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIZombieHostile;
import dungeonmania.components.aistates.AIRunAway;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 100);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 10, 10);
	
	public ZombieToast(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "zombie_toast", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
		aiComponent.registerState(new AIZombieHostile(aiComponent, this));
		aiComponent.registerState(new AIRunAway(aiComponent, this, moveComponent));
		aiComponent.changeState("ZombieHostile");
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		String playerState = getDungeon().getPlayer().getStatus();
		switch (playerState) {
			case "invincible":
				aiComponent.changeState("enemyRunAway");
				break;
			default:
			aiComponent.changeState("ZombieHostile");
			break;
		}
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
