package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.buildable.Buildable;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.battles.AttackItemComponent;
import dungeonmania.components.battles.Attack;
import dungeonmania.components.battles.BowAttack;



/**
 * Bow Entity can attack a second time
 */
public class Bow extends Buildable {
	
	final private int totalDurability = 3;
	final private int damage = 4;

	public AttackItemComponent attackItemComponent;
	public CollectableComponent collectableComponent;

	
	public Bow(Dungeon dungeon, Position position, CollectableState collectableState) {
		super(dungeon,  BuildableEnum.BOW.getType(), position, false, BuildableEnum.BOW.getRecipes());
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		attackItemComponent = new AttackItemComponent(this, 2, totalDurability, damage, new BowAttack());
		
	}
			
	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
