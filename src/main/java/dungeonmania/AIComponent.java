package dungeonmania;

import java.util.ArrayList;
import java.util.HashMap;

public class AIComponent extends Component implements Observer {
        private HashMap<String, AIState> stateMap = new HashMap<>();
        private AIState currentState;


        /**
         * constructor for AI component
         * @param OwningEntity
         * @param updatedOrder
         * @param currentState
         * @param stateName
         */
        public AIComponent(Entity OwningEntity, int updatedOrder, AIState currentState, 
                        String stateName) {
                super(OwningEntity, updatedOrder);
                this.currentState = currentState;
                stateMap.put(stateName, currentState);
        }

        /**
         * registering AI state of a component
         * @param currentState
         */
        public void registerAIState(AIState currentState) {
                AIState.registerState(currentState);
        }

        /**
         * removing AI State
         * @param currentState
         */
        public void changeAIState(AIState currentState) {
                AIState.removeState(currentState);
        }

        @Override
        public void update(Subject componentA) {
                if(componentA instanceof Component) {
			this.updatedOrder = ((Component) componentA).getUpdatedOrder();
		}
        }


}
