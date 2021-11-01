package dungeonmania.components;

import java.util.HashMap;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class AIComponent extends Component {
    
	private HashMap<String, AIState> stateMap = new HashMap<>();
    private AIState currentState = null;

    public AIComponent(Entity owningEntity, int updateOrder) {
        super(owningEntity, updateOrder);
    }
    
    public void processInput(InputState inputState) {
		if (currentState == null) return;
    	// garbage dirt-brain level pathfinding
		currentState.processInput(inputState);
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
     * registering a new possible AI state
     * @param currentState
     */
    public void registerState(AIState state) {
        stateMap.put(state.getName(), state);
    }

    public AIState getAISate() { return currentState; }
}
