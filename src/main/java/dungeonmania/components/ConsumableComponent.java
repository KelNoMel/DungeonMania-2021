
package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

// For all items that can be used (consume, craft, place)
public class ConsumableComponent extends Component {
   
	// Most objects have just 1 durability
    private int curDurability;
    // When multiple of the item is required (in crafting)
    private int numObjUsed;

    public ConsumableComponent(Entity owningEntity, int updateOrder, int durability, int uses) {
        super(owningEntity, updateOrder);
        this.curDurability = durability;
        this.numObjUsed = uses;
    }

    public void processInput(InputState input) {
        
        // Determine if the required number of items were already used
        int frequency = 0;
        for (String item : getEntity().getDungeon().getPlayer().getUsedList().values()) {
            if (item == input.getItemUsed()) {
                frequency = frequency + 1;
            }
        }

        
        // Item is in inventory, matches input item type and
        // not enough of the item is used yet
        if (getEntity().getDungeon().getPlayer().getInventory().contains(getEntity())
            && input.getItemUsed() == getEntity().getType() 
            && frequency < numObjUsed) {
            
                // All prerequisites are filled, add item to usedList
                getEntity().getDungeon().getPlayer().getUsedList().put(getEntity().getId(), getEntity().getType());

                curDurability = curDurability - 1;
                if (curDurability == 0) {
                    getEntity().setState(EntityState.DEAD);
                }
        }


    }
    
    public void updateComponent() {}

}
