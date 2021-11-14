package dungeonmania.entities.buildable;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class BuildableFactory {
    public static Entity build(String buildable, Dungeon dungeon) {
        // Assuming all buildibles become an item in the inventory with 
		// arbitrary position
		Position pos = new Position(0, 0);
        switch(buildable) {
			case "bow":
                return new Bow(dungeon, pos.asLayer(0));
			case "shield":
                return new Shield(dungeon, pos.asLayer(0));
			case "sceptre":
                return new Sceptre(dungeon, pos.asLayer(0));
			case "midnight_armour":
                return new MidnightArmour(dungeon, pos.asLayer(0));
			default:
				throw new IllegalArgumentException(buildable + " is not buildable type that has been implemented");
		}
    }
}
