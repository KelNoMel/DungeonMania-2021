package dungeonmania.entities;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public class Wall extends Entity {

	public Wall(Dungeon dungeon, Position position) {
		super(dungeon, "wall", position, false);
	}

	protected void updateEntity() {}

}
