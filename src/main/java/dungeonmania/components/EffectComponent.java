package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

// For all items that can be used (consume, craft, place)
public class EffectComponent extends Component {
    // Timer that allows component to remove itself when finished
    private int timer = 10;

    public EffectComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }

    public void processInput(InputState input) {
    }
    
    // Tick down the component to zero.
    // When timer is zero, reset the status to normal and remove the component
    public void updateComponent() {
        timer = timer - 1;
        if (timer == 0) {
            getEntity().getDungeon().getPlayer().setStatus("normal");
            getEntity().removeComponent(this);
        }
    }

}