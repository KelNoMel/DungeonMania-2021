package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class Exit extends Entity {

	public Exit(Dungeon dungeon, Position position) {
		super(dungeon, "exit", position, false, EntityUpdateOrder.OTHER);
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

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
