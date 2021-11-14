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
    
    public void useItem() {
        durability--;
        System.out.println("durability is now " + durability);
        if (durability <= 0) {
            getEntity().setState(EntityState.DEAD);
            System.out.println("battle item set to dead");
        }
    }

    public boolean isBroken() {
        return getEntity().getState().equals(EntityState.DEAD);
    }

    public void processInput(InputState inputState) {}

    public void updateComponent() {}

    public void loadJSONComponentSpecific(JSONObject entityData) {
    	if (entityData.has("durability")) {
    		durability = entityData.getInt("durability");
    	}
    }
    
	public void addJSONComponentSpecific(JSONObject entityJSON) {
		entityJSON.put("durability", durability);
	}

}
