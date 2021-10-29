package dungeonmania.components;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

// For all items that can be used (consume, craft, place)
public class ConsumableComponent extends Component {
    private Dungeon owningDungeon;
    // Most objects have just 1 durability
    private int curDurability;
    // When multiple of the item is required (in crafting)
    private int numObjUsed;


    public ConsumableComponent(Entity owningEntity, int updateOrder,
                            Dungeon dungeon, int durability, int uses) {
        super(owningEntity, updateOrder);
        this.owningDungeon = dungeon;
        this.curDurability = durability;
        this.numObjUsed = uses;
    }

    public void processInput(InputState input) {
        
        // Determine if the required number of items were already used
        int frequency = 0;
        for (String item : owningDungeon.getPlayer().getUsedList().values()) {
            if (item == input.getItemUsed()) {
                frequency = frequency + 1;
            }
        }

        // Item is in inventory, matches input item type and
        // not enough of the item is used yet
        if (getEntity().getState() == EntityState.INVENTORY
            && input.getItemUsed() == getEntity().getType() 
            && frequency < numObjUsed) {
            
                // All prerequisites are filled, add item to usedList
                owningDungeon.getPlayer().getUsedList().put(owningEntity.getId, owningEntity.getType);
        }


    }
    
    public void updateComponent() {}

}
