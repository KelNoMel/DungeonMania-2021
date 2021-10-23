package dungeonmania;

import java.util.HashMap;

public class AIComponent extends Component implements Observer {
    
	private HashMap<String, AIState> stateMap = new HashMap<>();
    private AIState currentState;

    public AIComponent(Entity OwningEntity, int updateOrder, AIState currentState, 
                    String stateName) {
        super(OwningEntity, updateOrder);
        this.currentState = currentState;
        stateMap.put(stateName, currentState);
    }
    
    public void update() {
		currentState.update();
	}
    
    public void update(Subject componentA) {
        if (componentA instanceof Component) {
        	updateOrder = ((Component) componentA).getUpdatedOrder();
        }
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
}
