package dungeonmania.entities.buildable;

import org.json.JSONObject;

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
                return new Bow(dungeon, pos.asLayer(0), new JSONObject());
			case "shield":
                return new Shield(dungeon,BuildableEnum.SHIELD.getType(), pos.asLayer(0), BuildableEnum.SHIELD.getRecipes(), new JSONObject());
			default:
				throw new IllegalArgumentException(buildable + " is not buildable type that has been implemented");
		}
    }



	// check requirements for every type resonse

	
	// check requirements for my one type then build entity that gets stored in inventory


	// add constructor that allows for a bow / shield to be found on the ground
	// the methods for this should be done once inside those functions and then 
	// work on that


}
