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
	private boolean hasBeenPlaced;
	
    public CollectableComponent(Entity owningEntity, int updateOrder, CollectableState collectableState) {
        super(owningEntity, updateOrder);
        this.collectableState = collectableState;
		this.hasBeenPlaced = false;
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
	    	
	    	if (d.isPlayerHere(getEntity().getPosition()) && hasBeenPlaced == false) {
		        // player is standing on the item
		        // item will be removed from entity list at the end of the tick
		        
	    		// Key pickup check
	    		d.transferToInventory(getEntity());
		        collectableState = CollectableState.INVENTORY;
		        // item is will be added to the inventory at the end of the tick
		        // TODO: some items may need to be used on the same tick as they 
		        // are picked up. Add in a common method
	        }
			this.hasBeenPlaced = false;
    	}
    }
    
    public void saveJSONComponentSpecific(JSONObject entityJSON) {
		entityJSON.put("collectState", collectableState.getName());
		entityJSON.put("placed", hasBeenPlaced);
	}
    
    public void loadJSONComponentSpecific(JSONObject entityData) {
		if (entityData.has("collectState")) {
    		collectableState = CollectableState.stateFromString(entityData.getString("collectState"));
    	}
		if (entityData.has("placed")) {
    		hasBeenPlaced = entityData.getBoolean("placed");
    	}
	}

	public void place() {
		if (collectableState == CollectableState.INVENTORY) {
	    	Dungeon d = getEntity().getDungeon();
			
	    	// Add to dungeon entitylist
			d.transferToMap(getEntity());
			// Change collectablestate
			collectableState = CollectableState.MAP;
			// Set entity position to where the player is standing
			getEntity().setPosition(d.getPlayer().getPosition());
			this.hasBeenPlaced = true;
    	}
	}

	public CollectableState getCollectableState() { return collectableState; }
	public void setCollectableState(CollectableState collectableState) { this.collectableState = collectableState; }
}
