package dungeonmania.entities.statics;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Exit extends Entity {

	public Exit(Dungeon dungeon, Position position) {
		super(dungeon, "exit", position, false);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

	public Boolean playerAtExit() {
		if (getDungeon().getPlayer().getPosition().equals(getPosition())) {
			return true;
		}
		return false;
	}

}
