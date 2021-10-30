package dungeonmania.entities.collectables;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Treasure extends Entity {

	private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);
	
	public Treasure(Dungeon dungeon, Position position) {
		super(dungeon, "treasure", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
