package dungeonmania.entities.statics;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    protected int movement_factor;
    
	public SwampTile(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
        // TODO: movement_factor
		super(dungeon, "swamp_tile", position, false, entitySpecificData);
	}

	protected void updateEntity() {}

	protected void inputEntity(InputState inputState) {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
    
}
