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
import dungeonmania.components.aistates.AIRandomHostile;
import dungeonmania.components.aistates.AIRunAway;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Mercenary extends Entity {
	final int maxHealth = 30;
	final int damage = 10;
	
	private String startState;
	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, maxHealth, damage);
	
	public Mercenary(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary", position, true, EntityUpdateOrder.OTHER);
		aiComponent.registerState(new AIMercHostile(aiComponent, this));
		aiComponent.registerState(new AIMercAlly(aiComponent, this));
		aiComponent.registerState(new AIRandomHostile(aiComponent, moveComponent));
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
		if (aiComponent.getAIState().getName().equals("MercAlly")) {
			return;
		}

		switch (playerState) {
			case "invincible":
				aiComponent.changeState("enemyRunAway");
				break;
			case "invisible":
				aiComponent.changeState("RandomHostile");
				break;
			default:
				aiComponent.changeState("MercHostile");
				break;
		}
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("aiState", aiComponent.getAIState().getName());
	}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
		startState = null;
		if (entitySpecificData.has("aiState")) {
			startState = entitySpecificData.getString("aiState");
		}
	}
}
