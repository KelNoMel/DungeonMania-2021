package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.exceptions.InvalidActionException;


/**
 * Buildable entities
 * 
 */
public abstract class Buildable extends Entity {
    protected Player player;
	protected List<Recipe> recipes;

    public Buildable(Dungeon dungeon, String type, Position position,  
        boolean isInteractable, List<Recipe> recipes, JSONObject entitySpecificData) throws InvalidActionException {
		// old constructor parameters
		// ArrayList<ArrayList<String>> requiredTypeConfigs, 
		// ArrayList<ArrayList<Integer>> requiredFreqConfigs


        super(dungeon, type, position, isInteractable, entitySpecificData);
        this.player = dungeon.getPlayer();
		this.recipes = recipes;
        Recipe recipe  = checkRequirements(player, recipes);
		if (recipe == null) {
			throw new InvalidActionException("player does not have sufficient items to craft the buildable");
		}
		useRequirements(recipe);
		//useRequirements(requiredTypes, requiredFreq);
    }

	public static List<String> response(Player player) {
		return new ArrayList<>(BuildableEnum.stream()
			.filter(e -> Buildable.checkRequirements(player, e.getRecipes()) != null)
			.map(e->e.getType()).collect(Collectors.toList()));
	}

	protected static Recipe checkRequirements(Player player, List<Recipe> recipes) {
		// count each type required and use the first acceptable configuration
		for (Recipe recipe : recipes) {
			boolean hasRequirementsflag = true;

			for (Ingredient ingredient : recipe.getIngredients()) {
				// search the entire inventory for every type of ingredient for every recipe
				// note this is not efficient. Ideally you keep subset of items and
				// keep searching that again and again (subset list or hash map linked list)
				ArrayList<Entity> currReq = new ArrayList<Entity>(player.getInventory().stream()
					.filter(e -> ingredient.getType().equals(e.getType())).collect(Collectors.toList()));
				if (currReq.size() < ingredient.getFreq()) {
					hasRequirementsflag = false;
					break;
				}
			}
			// remove one acceptable configuration of requirements
			if (hasRequirementsflag) {
				return recipe;
			}
		}
		return null;
	}

	protected void useRequirements(Recipe recipe) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			int amount = ingredient.getFreq();
			String type = ingredient.getType();
			for (int freq = 0; freq < amount ; freq++) {
				player.removeTypeFromInventory(type);
			}
		}

	}

}
