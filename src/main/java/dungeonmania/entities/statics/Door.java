package dungeonmania.entities.statics;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Door extends Entity {

	private static boolean isUnlocked;
	private String doorId;
	private static List<String> DoorList = new ArrayList<String>();

	public Door(Dungeon dungeon, Position position, String doorId) {
		super(dungeon, "door", position, true);
		isUnlocked = false;
		this.doorId = doorId;
		DoorList.add(doorId);
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


}
