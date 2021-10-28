package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.Player;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;

public abstract class Buildable extends Entity {
    protected Player player = null;

    public Buildable(Dungeon dungeon, String type, Position position,  
        boolean isInteractable, Player player, ArrayList<ArrayList<String>> requiredTypeConfigs, 
		ArrayList<ArrayList<Integer>> requiredFreqConfigs) {
        super(dungeon, type, position, isInteractable);
        this.player = player;
        checkRequirements(requiredTypeConfigs, requiredFreqConfigs);
    }

    protected boolean checkRequirements(ArrayList<ArrayList<String>> requiredTypeConfigs, 
		ArrayList<ArrayList<Integer>> requiredFreqConfigs) {
		// Note this is not an efficient method as it gets a new inventory multiple times
		
		// count each type required and use the first acceptable configuration
		int configLength = requiredTypeConfigs.size();
		for (int j = 0; j < configLength; j++) {
			// ideally this would be tuples
			// get the list of all types 
			final Integer jj = j;
			ArrayList<String> requiredTypes = requiredTypeConfigs.get(jj);
			ArrayList<Integer> requiredFreq = requiredFreqConfigs.get(jj);
			ArrayList<Entity> available = new ArrayList<>(player.getInventory().stream()
				.filter(e -> requiredTypes.contains(e.getType())).collect(Collectors.toList()));
				
			int reqLength = requiredTypes.size();
			boolean hasRequirementsflag = true;
			for (int i = 0; i < reqLength; i++) {
				final int ii = i;
				ArrayList<Entity> currReq = new ArrayList<Entity>(available.stream()
					.filter(e -> requiredTypes.get(ii).equals(e.getType())).collect(Collectors.toList()));
				if (currReq.size() < requiredFreq.get(ii)) {
					hasRequirementsflag = false;
					break;
				}
			}
			// remove one acceptable configuration of requirements
			if (hasRequirementsflag) {
				useRequirements(requiredTypes, requiredFreq);
			}
		}

		throw new InvalidActionException("player does not have sufficient items to craft the buildable");

	}

	protected void useRequirements(ArrayList<String> requiredTypes, 
		ArrayList<Integer> requiredFreq) {
		int reqLength = requiredTypes.size();
		for (int i = 0; i < reqLength; i++) {
			for (int freq = 0; freq < requiredFreq.get(i); freq++) {
				player.removeTypeFromInventory(requiredTypes.get(i));
			}
		}
	}
}
