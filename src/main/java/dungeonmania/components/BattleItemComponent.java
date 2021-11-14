package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.InputState;

public class BattleItemComponent extends Component {
    private int durability;
    
    public BattleItemComponent(Entity owningEntity, int updateOrder, int durability) {
        super(owningEntity, updateOrder);
        this.durability = durability;
        
    }
    
    public void processInput(InputState inputState) {}
    public void updateComponent() {}
    
    public void saveJSONComponentSpecific(JSONObject entityJSON) {
    	entityJSON.put("durability", durability);
    }
    
    public void loadJSONComponentSpecific(JSONObject entityData) {
    	if (entityData.has("durability")) {
    		durability = entityData.getInt("durability");
    	}
    }
    
    public void useItem() {
        durability--;
        if (durability <= 0) {
            getEntity().setState(EntityState.DEAD);
        }
    }

    public boolean isBroken() {
        return getEntity().getState().equals(EntityState.DEAD);
    }
}
