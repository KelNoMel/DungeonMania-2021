package dungeonmania.entities.buildable;

import java.util.ArrayList;
import java.util.Arrays;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;

public class Shield extends Buildable {
	// durability for the number of times a shield can take a hit
	private int durability = 5;
	// decrease the effect of enemy attacks by 2 damage
	private int armour = 2;
	
	
	
	public Shield(Dungeon dungeon, Position position, Player player) {
		super(dungeon, "shield", position, false, player, 
		new ArrayList<ArrayList<String>>(Arrays.asList(
			new ArrayList<>(Arrays.asList("wood","treasure")), 
			new ArrayList<>(Arrays.asList("wood","key")) 
			)), 
			new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<>(Arrays.asList(2,1)), 
				new ArrayList<>(Arrays.asList(2,1))
			))
		);
	}

	public Shield(Dungeon dungeon, Position position) {
		this(dungeon, position, null);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		
	}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

}
