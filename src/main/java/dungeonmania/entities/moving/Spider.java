package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AISpiderHostile;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Spider extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.GHOST);
	
	public Spider(Dungeon dungeon, Position position) {
		super(dungeon, "spider", position, false);
		aiComponent.registerState(new AISpiderHostile(aiComponent, this));
		aiComponent.changeState("SpiderHostile");
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}

}
