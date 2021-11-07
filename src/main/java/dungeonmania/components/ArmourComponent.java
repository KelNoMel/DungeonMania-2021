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

    public ArmourComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }

    public void processInput(InputState inputState) {}
    
    public void updateComponent() {}

    public int getArmour() {
        return armour;
    }
}
