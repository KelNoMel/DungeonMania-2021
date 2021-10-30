package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.handler.ContextHandler.Availability;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.buildable.Buildable;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;

/**
 * Bow Entity can shoot from a range
 */
public class Bow extends Buildable {
	public Bow(Dungeon dungeon, Position position) {
		super(dungeon,  BuildableEnum.BOW.getType(), position, false, 
			BuildableEnum.BOW.getRecipes());
	}
			
	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
