package dungeonmania.components;


import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class CollectableComponent extends Component {

	private CollectableState collectableState;
	
    public CollectableComponent(Entity owningEntity, int updateOrder, CollectableState collectableState) {
        super(owningEntity, updateOrder);
        this.collectableState = collectableState;
    }

    public void processInput(InputState inputState) {}
    
    public void updateComponent() {
    	if (collectableState == CollectableState.MAP) {
	    	Dungeon d = getEntity().getDungeon();
	    	
	    	if (d.isPlayerHere(getEntity().getPosition())) {
		        // player is standing on the item
		        // item will be removed from entity list at the end of the tick
		        
		        d.transferToInventory(getEntity());
		        collectableState = CollectableState.INVENTORY;
		        // item is will be added to the inventory at the end of the tick
		        // TODO: some items may need to be used on the same tick as they 
		        // are picked up. Add in a common method
	        }
    	}
    }

	public CollectableState getCollectableState() { return collectableState; }
	public void setCollectableState(CollectableState collectableState) { this.collectableState = collectableState; }
}
