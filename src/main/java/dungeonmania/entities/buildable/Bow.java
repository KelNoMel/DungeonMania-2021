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

/**
 * Bow Entity can shoot from a range
 */
public class Bow extends Buildable {
	public Bow(Dungeon dungeon, Position position) {
		this(dungeon, position, null);
	}

	public Bow(Dungeon dungeon, Position position, Player player) {
		super(dungeon, "bow", position, false, player,
			new ArrayList<ArrayList<String>>(Arrays.asList(
				new ArrayList<>(Arrays.asList("arrows","wood")) 
			)), 
			new ArrayList<ArrayList<Integer>>(Arrays.asList(
				new ArrayList<>(Arrays.asList(3,1))
			))
		);

	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
