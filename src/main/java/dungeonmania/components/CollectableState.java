package dungeonmania.components;

/**
 * CollectableState describes whether an entity with a CollectableComponent (item)
 * is on the map or in the player's inventory.
 */
public enum CollectableState {
	MAP("MAP"), INVENTORY("INVENTORY");
	
	private String name;
	CollectableState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static CollectableState stateFromString(String name) {
		switch (name) {
			case "MAP":
				return MAP;
			case "INVENTORY":
				return INVENTORY;
			default:
				return MAP;
		}
	}
}
