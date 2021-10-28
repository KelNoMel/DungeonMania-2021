package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Wall extends Entity {

	public Wall(Dungeon dungeon, Position position) {
		super(dungeon, "wall", position, false);
	}

	protected void updateEntity() {}

	protected void inputEntity(InputState inputState) {}

}