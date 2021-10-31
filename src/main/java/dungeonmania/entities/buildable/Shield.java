package dungeonmania.entities.buildable;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.battles.ArmourComponent;
import dungeonmania.components.battles.BattleItemComponent;

// debugging
import dungeonmania.entities.buildable.Bow;


public class Shield extends Buildable {
	final private int totalDurability = 5;
	final private int armour = 2;
	
	public ArmourComponent armourComponent;
	public CollectableComponent collectableComponent;

	// if a buildable is found on the map
	public Shield(Dungeon dungeon, Position position, CollectableState collectableState) {
		super(dungeon,  BuildableEnum.SHIELD.getType(), position, false, BuildableEnum.SHIELD.getRecipes());
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		armourComponent = new ArmourComponent(this, 2, totalDurability, armour);
	}

	@Override
	protected void inputEntity(InputState inputState) {

	}
	@Override
	protected void updateEntity() {
		
	}

	// remove shield after taking the final hit
	// shield observes the player battling (each battle / each time the player is attacked)

}
