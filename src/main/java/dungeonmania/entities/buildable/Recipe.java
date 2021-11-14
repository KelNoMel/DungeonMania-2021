package dungeonmania.entities.buildable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.ZombieToast;

/**
 * Strongly typed tuple structure for keeping buildable resource requirements
 * 
 */
public class Recipe {
    private List<ArrayList<Ingredient>> recipe = new ArrayList<>();

    public Recipe(List<ArrayList<Ingredient>> recipe) {
        this.recipe = recipe;
    }

    protected List<Ingredient> checkRequirements(Player player, String type) {
		if (type.equals("midnight_armour") &&
				player.getDungeon().getEntities().numEntitiesOfType(ZombieToast.class) > 0) {
			return null;
		}
    	
    	List<Ingredient> ingredients = new ArrayList<>();

        for (List<Ingredient> recipeStep : recipe) {
            boolean hasRequirementsflag = false;

            for (Ingredient ingredient : recipeStep) {
                ArrayList<Entity> currReq = new ArrayList<Entity>(player.getInventory().stream()
					.filter(e -> ingredient.getType().equals(e.getType()))
                    .collect(Collectors.toList()));
                // check if there are enough of the required ingredient type
                if (currReq.size() >= ingredient.getFreq()) {
                    ingredients.add(ingredient);
                    hasRequirementsflag = true;
                    break;
                }
            }
            // if there wasn't enough of any of the ingredients for this step
            // if no then the recipe is not possible
            if (!hasRequirementsflag) {
                return null;
            }
        }
        return ingredients;
	}
}
