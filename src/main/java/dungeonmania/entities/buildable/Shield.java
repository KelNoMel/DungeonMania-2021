package dungeonmania.entities.buildable;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import java.util.Arrays;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;


public class Shield extends Buildable {
	// durability for the number of times a shield can take a hit
	private int durability = 5;
	// decrease the effect of enemy attacks by 2 damage
	private int armour = 2;
	
	
	
	public Shield(Dungeon dungeon, String type, Position position, List<Recipe> recipes) {
		super(dungeon, type, position, false, recipes);
	}

	// if a buildable is found on the map
	public Shield(Dungeon dungeon, Position position) {
		super(dungeon,  BuildableEnum.SHIELD.getType(), position, false, 
			BuildableEnum.SHIELD.getRecipes());
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		
	}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
}
