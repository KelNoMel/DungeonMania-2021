package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.components.Component;

public class ArmourComponent extends Component {
    // TODO force the entity to be of type BattleItem
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

    public void loadJSONComponentSpecific(JSONObject entityData) {}
	public void addJSONComponentSpecific(JSONObject entityJSON) {}
}
