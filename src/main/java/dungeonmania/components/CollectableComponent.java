package dungeonmania.components;


import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Key;

/**
 * CollectableComponent will automatically add a component from the map to the 
 * player's inventory when the player steps on it.
 */
public class CollectableComponent extends Component {

	private CollectableState collectableState;
	
    public CollectableComponent(Entity owningEntity, int updateOrder, CollectableState collectableState) {
        super(owningEntity, updateOrder);
        this.collectableState = collectableState;
    }

    public void processInput(InputState inputState) {}
    
    public void updateComponent() {
    	Entity parent = getEntity();
    	// Ensure if a key, only one can be picked up at a time
		if (parent instanceof Key && parent.getDungeon().getPlayer().getInventory().numEntitiesOfType(Key.class) > 0) {
			return;
		}
    	
    	if (collectableState == CollectableState.MAP) {
	    	Dungeon d = getEntity().getDungeon();
	    	
	    	if (d.isPlayerHere(getEntity().getPosition())) {
		        // player is standing on the item
		        // item will be removed from entity list at the end of the tick
		        
	    		// Key pickup check
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

	public void loadJSONComponentSpecific(JSONObject entityData) {}
	public void addJSONComponentSpecific(JSONObject entityJSON) {}
}
