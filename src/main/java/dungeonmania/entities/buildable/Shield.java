package dungeonmania.entities.buildable;

import java.util.ArrayList;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;

public class Shield extends Buildable {
	Player player = null;
	// durability for the number of times a shield can take a hit

	// decrease the effect of enemy attacks by 2 damage
	
	public Shield(Dungeon dungeon, Position position) {
		super(dungeon, "shield", position, false, null, new ArrayList<ArrayList<String>>(), 
		new ArrayList<ArrayList<Integer>>());
	}


	public Shield(Dungeon dungeon, Position position, Player player) {
		super(dungeon, "shield", position, false, player, new ArrayList<ArrayList<String>>(), 
		new ArrayList<ArrayList<Integer>>());
		this.player = player;

	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		
	}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

}
