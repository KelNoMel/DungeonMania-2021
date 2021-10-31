package dungeonmania.components;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

// For all items that can be used (consume, craft, place)
public class EffectComponent extends Component {
    // Timer that allows component to remove itself when finished
    private int timer = 10;

    public EffectComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }

    public void processInput(InputState input) {
    }
    
    public void updateComponent() {
        timer = timer - 1;
        if (timer == 0) {
            getEntity().getDungeon().getPlayer().setStatus("normal");
            owningEntity.removeComponent(this);
        }
    }

}