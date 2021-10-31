package dungeonmania.components.battles;

import dungeonmania.InputState;
import dungeonmania.components.Component;
import dungeonmania.Observer;
import dungeonmania.Subject;
import dungeonmania.entities.Entity;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;

/**
 * BattleItemComponent is a base class of common accessories used for battle.
 */
public abstract class BattleItemComponent extends Component implements Observer {
    protected int durability;
    
    public BattleItemComponent(Entity owningEntity, int updateOrder, int durability) {
        super(owningEntity, updateOrder);
        this.durability = durability;
    }

    public void updateObserver(Subject sub) {
        // add weapon if another attack is possible this battle
        battle(sub);

        // if we use it durability--
    }
    
    public void processInput(InputState inputState) {}
    
    public void updateComponent() {}

    /**
     * Removes an items effects and the item itself. To be used when the item
     * has lost its durability and has broken.
     */
    protected void removeItem(Subject sub) {
        removeItemEffects(sub);
        sub.detach(this);
    }

    protected abstract void removeItemEffects(Subject sub);
    protected abstract void battle(Subject sub);

}
