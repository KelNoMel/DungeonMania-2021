package dungeonmania.components;


import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class CollectibleComponent extends Component {

    public CollectibleComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }

    public void processInput(InputState inputState) {
        Dungeon d = getEntity().getDungeon();
    	
    	if (d.isPlayerHere(getEntity().getPosition())) {
	        // player is standing on the item
	        // item will be removed from entity list at the end of the tick
	        
	        d.transferToInventory(getEntity());
	        // item is will be added to the inventory at the end of the tick
	        // TODO: some items may need to be used on the same tick as they 
	        // are picked up. Add in a common method
        }
    }

    public void updateComponent() {}

}
