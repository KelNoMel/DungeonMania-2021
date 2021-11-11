package dungeonmania.entities.statics;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.redstone.RedstoneConduit;
import dungeonmania.entities.redstone.RedstoneSource;
import dungeonmania.util.Position;

public class FloorSwitch extends Entity implements RedstoneSource {
	
	private boolean isTriggered;
	private List<RedstoneConduit> observers = new ArrayList<>();
	
	public FloorSwitch(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "switch", position, false, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		boolean hasboulder = false;
		for (Entity entity : getDungeon().getEntitiesAtPosition(getPosition())) {
			
			if (entity instanceof Boulder) {
				isTriggered = true;
				hasboulder = true;
			} 
		}
		if(!hasboulder) isTriggered = false;
	}

	public boolean isTriggered() {
		return isTriggered;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
