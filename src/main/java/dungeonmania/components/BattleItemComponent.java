package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.Component;
import dungeonmania.util.Position;

public class BattleItemComponent extends Component {
    private int durability;
    
    public BattleItemComponent(Entity owningEntity, int updateOrder, int durability) {
        super(owningEntity, updateOrder);
        this.durability = durability;
        
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

    @Override
    public void processInput(InputState inputState) {}

    @Override
    public void updateComponent() {}

}
