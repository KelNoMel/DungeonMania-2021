package dungeonmania.components;

import java.util.HashMap;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class AIComponent extends Component {
    
	private HashMap<String, AIState> stateMap = new HashMap<>();
    private AIState currentState;

    public AIComponent(Entity owningEntity, int updateOrder, AIState currentState, 
                    String stateName) {
        super(owningEntity, updateOrder);
        this.currentState = currentState;
        stateMap.put(stateName, currentState);
    }
    
    public void updateComponent() {
		currentState.update();
	}
    
    /**
     * Transition between states
     * @param currentState
     */
    public void changeState(String newState) {
        currentState.onExit();
    	
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

	public void processInput(InputState inputState) {}
}
