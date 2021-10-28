package dungeonmania.components;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

public class CollectibleComponent extends Component {
    private Dungeon owningDungeon;


    public CollectibleComponent(Entity owningEntity, int updateOrder, Dungeon dungeon) {
        super(owningEntity, updateOrder);
        this.owningDungeon = dungeon;
    }

    public void processInput(InputState inputState) {
        if (getEntity().getState() == EntityState.ACTIVE
            && owningDungeon.isPlayerHere(getEntity().getPosition())) {
            // player is standing on the item
            // item will be removed from entity list at the end of the tick
            getEntity().setState(EntityState.INVENTORY);
            // item is will be added to the inventory at the end of the tick
            owningDungeon.addInventory(getEntity());
            // TODO: some items may need to be used on the same tick as they 
            // are picked up. Add in a common method

        }


    }
    
    public void updateComponent() {}

}
