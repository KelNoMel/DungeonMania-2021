package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class ArmourComponent extends Component {

	private int armour;

    public ArmourComponent(Entity owningEntity, int updateOrder, int armour) {
        super(owningEntity, updateOrder);
        try {
            if (owningEntity.getComponent(BattleItemComponent.class) == null) {
                throw new Exception("Incorrect usage: ArmourComponent requires an entity with a BattleItemComponent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.armour = armour;
    }

    public void processInput(InputState inputState) {}
    public void updateComponent() {}

    public void saveJSONComponentSpecific(JSONObject entityJSON) {
    	entityJSON.put("armour", armour);
    }
    
    public void loadJSONComponentSpecific(JSONObject entityData) {
    	if (entityData.has("armour")) {
    		armour = entityData.getInt("armour");
    	}
    }
    
    public void useItem() {
    	getBattleItem().useItem();
    }
    
    public boolean isBroken() {
    	return getBattleItem().isBroken();
    }
    
    private BattleItemComponent getBattleItem() {
    	return getEntity().getComponent(BattleItemComponent.class);
    }
    
    public int getArmour() { return armour; }
}
