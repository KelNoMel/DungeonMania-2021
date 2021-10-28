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
	private Player player = null;

	public Bow(Dungeon dungeon, Position position) {
		super(dungeon, "bow", position, false);
	}

	public Bow(Dungeon dungeon, Position position, Player player) {
		super(dungeon, "bow", position, false);
		this.player = player;

		// ArrayList<String> requiredTypes = new ArrayList<>(Arrays.asList("wood", "arrow"));
		// ArrayList<Integer> requiredFreq = new ArrayList<>(Arrays.asList(1,3));
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}

}
