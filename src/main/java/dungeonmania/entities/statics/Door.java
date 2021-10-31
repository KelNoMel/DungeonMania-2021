package dungeonmania.entities.statics;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Door extends Entity {
	private static boolean isUnlocked;
	private String doorId;
	private static List<String> DoorList = new ArrayList<String>();

	public Door(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "door", position, true, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
	}

	public String getDoorId() {
		return doorId;
	}

	public static boolean isUnlocked() {
		isUnlocked = true;
		return isUnlocked;
	}

	public static List<String> getDoorList() {
		return DoorList;
	}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
