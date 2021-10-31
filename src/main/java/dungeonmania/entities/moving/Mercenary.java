package dungeonmania.entities.moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIMercAlly;
import dungeonmania.components.aistates.AIMercHostile;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Mercenary extends Entity {

	public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	
	public Mercenary(Dungeon dungeon, Position position) {
		super(dungeon, "mercenary", position, true);
		aiComponent.registerState(new AIMercHostile(aiComponent, this));
		aiComponent.registerState(new AIMercAlly(aiComponent, this));
		aiComponent.changeState("MercHostile");
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
}
