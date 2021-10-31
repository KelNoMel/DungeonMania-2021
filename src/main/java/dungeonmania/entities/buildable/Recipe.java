package dungeonmania.entities.buildable;

import java.util.ArrayList;
import java.util.List;

/**
 * Strongly typed tuple structure for keeping buildable resource requirements
 * 
 */
public class Recipe {
    private List<Ingredient> ingredients;

    public Recipe(List<String> requiredType, List<Integer> requiredFreq) {
        this.ingredients = createRecipe(requiredType, requiredFreq);
    }

    public static List<Ingredient> createRecipe(List<String> requiredType, 
        List<Integer> requiredFreq) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        int recipeLength = requiredType.size();
        for (int i = 0; i < recipeLength; i++) {
            final int ii = i;
            ingredients.add(new Ingredient(requiredType.get(ii), requiredFreq.get(ii)));
        }
        return ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Creates a list of acceptable requirement configurations. Each configuration
     * is a list of Recipe tuples
     * @param requiredTypeConfigs
     * @param requiredFreqConfigs
     * @return list of acceptable requirements to build a buildable
     */
    public static List<Recipe> createRecipes (
        ArrayList<ArrayList<String>> requiredTypeConfigs, 
        ArrayList<ArrayList<Integer>> requiredFreqConfigs) {
        
        ArrayList<Recipe> recipes = new ArrayList<>();
        int recipesListLength = requiredTypeConfigs.size();
        for (int j = 0; j < recipesListLength; j++) {
            recipes.add(
                new Recipe(requiredTypeConfigs.get(j), requiredFreqConfigs.get(j)));
        }
        return recipes;
    }
}
