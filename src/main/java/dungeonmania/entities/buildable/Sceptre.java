package dungeonmania.entities.buildable;


import java.util.List;
import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.ArmourComponent;
import dungeonmania.components.BattleItemComponent;

public class Sceptre extends Buildable {
	// if a buildable is found on the map
	public Sceptre(Dungeon dungeon, Position position, JSONObject entitySpecificData)  {
		super(dungeon,  BuildableEnum.SCEPTRE.getType(), position, false, BuildableEnum.SCEPTRE.getRecipe(), entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {}
	protected void updateEntity() {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}

