package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIMercAlly;
import dungeonmania.components.aistates.AIMercHostile;
import dungeonmania.components.aistates.AIMercConfused;
import dungeonmania.components.aistates.AIRunAway;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Mercenary extends Entity {
	
	private String startState;
	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 30, 10);
	
	public Mercenary(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "mercenary", position, true, EntityUpdateOrder.OTHER, entitySpecificData);
		aiComponent.registerState(new AIMercHostile(aiComponent, this));
		aiComponent.registerState(new AIMercAlly(aiComponent, this));
		aiComponent.registerState(new AIMercConfused(aiComponent, this));
		aiComponent.registerState(new AIRunAway(aiComponent, this, moveComponent));
		if (startState == null) {
			aiComponent.changeState("MercHostile");			
		} else {
			aiComponent.changeState(startState);
			if (startState.equals("MercAlly")) {
				setInteractable(false);
			}
		}
	}

	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {
		String playerState = getDungeon().getPlayer().getStatus();
		// Don't change AI state if ally
		if (aiComponent.getAISate().getName().equals("MercAlly")) {
			return;
		}

		switch (playerState) {
			case "invincible":
				aiComponent.changeState("enemyRunAway");
				break;
			case "invisible":
				aiComponent.changeState("MercConfused");
				break;
			default:
				aiComponent.changeState("MercHostile");
				break;
		}
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("aiState", aiComponent.getAISate().getName());
	}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		startState = null;
		if (entitySpecificData.has("aiState")) {
			startState = entitySpecificData.getString("aiState");
		}
	}
}
