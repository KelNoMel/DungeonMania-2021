package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.InputState;


/**
 * Buildable entities
 * 
 */
public abstract class Buildable extends Entity {
    protected Player player;

    public Buildable(Dungeon dungeon, String type, Position position,  
        boolean isInteractable, Recipe recipe, JSONObject entitySpecificData) throws InvalidActionException {
       
		super(dungeon, type, position, isInteractable, EntityUpdateOrder.OTHER, entitySpecificData);
        this.player = dungeon.getPlayer();
		List<Ingredient> ingredients = recipe.checkRequirements(player);
		if (ingredients == null) {
			throw new InvalidActionException("player does not have sufficient items to craft the buildable");
		}
		useRequirements(ingredients);
    }

	public static List<String> response(Player player) {
		return new ArrayList<>(BuildableEnum.stream()
            .filter(e -> e.getRecipe().checkRequirements(player) != null)
            .map(e->e.getType())
            .collect(Collectors.toList()));
	}

	protected void useRequirements(List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			int amount = ingredient.getFreq();
			String type = ingredient.getType();
			for (int freq = 0; freq < amount ; freq++) {
				player.removeTypeFromInventory(type);
			}
		}
	}

	protected abstract void updateEntity();
	protected abstract void inputEntity(InputState inputState);

}
