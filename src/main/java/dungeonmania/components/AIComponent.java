package dungeonmania.components;

import java.util.HashMap;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class AIComponent extends Component {
    
	private HashMap<String, AIState> stateMap = new HashMap<>();
    private AIState currentState = null;
    private AIState prevState = null;
    private int temporaryCooldown = 0;

    public AIComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }
    
    public void processInput(InputState inputState) {
		if (currentState == null) return;
    	// garbage dirt-brain level pathfinding
		currentState.processInput(inputState);
        
        // switch back to previous state
        if (prevState != null) {
            temporaryCooldown--;
            if (temporaryCooldown <= 0) {
                currentState = prevState;
                prevState = null;
                System.out.println(getEntity().getType() + " has changed back to " + getEntity().getComponent(AIComponent.class).getAISate().getName());
            }
        }
	}
    
    public void updateComponent() {
    	if (currentState == null) return;
    	currentState.updateState();
	}
    
    /**
     * Transition between states
     * @param currentState
     */
    public void changeState(String newState) {
        if (currentState != null) {
        	currentState.onExit();        	
        }
    	
        if ((currentState = stateMap.get(newState)) != null) {
        	currentState.onEnter();
        }
    }

    /**
     * Change from the current state to a new state for specified number of ticks
     * Note: Even if the state is changed after this functions has been called
     *      the AIComponent will still revert to the previous state eventually 
     * @param newState
     * @param ticks
     */
    public void temporaryChangeState(String newState, int ticks) {
        prevState = currentState;
        temporaryCooldown = ticks;
        changeState(newState);
    }

    /**
     * registering a new possible AI state
     * @param currentState
     */
    public void registerState(AIState state) {
        stateMap.put(state.getName(), state);
    }

    public AIState getAISate() { return currentState; }

	public void loadJSONComponentSpecific(JSONObject entityData) {
		
	}

	public void addJSONComponentSpecific(JSONObject entityJSON) {}
}
