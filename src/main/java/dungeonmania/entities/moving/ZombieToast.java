package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIRandomHostile;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class ZombieToast extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 100);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 10, 10);
	
	public ZombieToast(Dungeon dungeon, Position position) {
		super(dungeon, "zombie_toast", position, false, EntityUpdateOrder.OTHER);
		aiComponent.registerState(new AIRandomHostile(aiComponent, moveComponent));
		aiComponent.changeState("RandomHostile");
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

}
