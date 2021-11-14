package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

public class SunStone extends Entity {
    private CollectableComponent collectableComp = new CollectableComponent(this, 1, CollectableState.MAP);

    public SunStone(Dungeon dungeon, Position position) {
		super(dungeon, "sun_stone", position, false, EntityUpdateOrder.OTHER);
	}

    protected void inputEntity(InputState inputState) {
    }

    protected void updateEntity() {   
    }

    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {        
    }

    public void addJSONEntitySpecific(JSONObject baseJSON) {}
}
