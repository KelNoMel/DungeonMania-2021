package dungeonmania.components;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

public class CollectibleComponent extends Component {
    private CollectibleState currentState;
    private Dungeon owningDungeon;


    public CollectibleComponent(Entity owningEntity, int updateOrder, CollectibleState currentState, Dungeon dungeon) {
        super(owningEntity, updateOrder);
        this.currentState = currentState;
        this.owningDungeon = dungeon;
    }


    public void processInput(InputState inputState) {
        if (currentState == CollectibleState.ENTITY
            && owningDungeon.isPlayerHere(getEntity().getPosition())) {
            // player is standing on the item
            // mark this item for inventory
            currentState = CollectibleState.INVENTORY;
            // item will be removed from entity list at the end of the tick
            getEntity().setState(EntityState.DEAD);
            // item is will be added to the inventory at the end of the tick
            owningDungeon.addInventory(getEntity());
        }
    }
    
    public void updateComponent() {}

}
