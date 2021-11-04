package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class SunStone extends Entity {
    private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);

    public SunStone(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "sun_stone", position, false, entitySpecificData);
	}

    @Override
    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void inputEntity(InputState inputState) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void updateEntity() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addJSONEntitySpecific(JSONObject baseJSON) {
        // TODO Auto-generated method stub
        
    }
}
