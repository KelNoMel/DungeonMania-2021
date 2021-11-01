
package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;

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
        
    	System.out.println(input.getItemUsed());
    	
    	Player player = getEntity().getDungeon().getPlayer();
    	
        // Determine if the required number of items were already used
        int frequency = 0;
        for (String item : player.getUsedList().values()) {
            if (item.equals(input.getItemUsed())) {
                frequency++;
            }
        }

        
        // Item is in inventory, matches input item type and
        // not enough of the item is used yet
        if (player.getInventory().contains(getEntity())
            && input.getItemUsed().equals(getEntity().getId()) 
            && frequency <= numObjUsed) {
            
                // All prerequisites are filled, add item to usedList
        		player.getUsedList().put(getEntity().getId(), getEntity().getType());

                curDurability = curDurability - 1;
                if (curDurability == 0) {
                    getEntity().setState(EntityState.DEAD);
                }
        }


    }
    
    public void updateComponent() {}

}
