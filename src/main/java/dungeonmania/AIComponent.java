package dungeonmania;

import java.util.HashMap;

public class AIComponent extends Component {
        private HashMap<String, AIState> stateMap = new HashMap<>();
        private AIState currentState;

        public AIComponent(Entity OwningEntity, int updatedOrder, AIState currentState, 
                        String stateName) {
                super(OwningEntity, updatedOrder);
                this.currentState = currentState;
                stateMap.put(stateName, currentState);
        }

        /**
         * This method put will replace the value of an existing key 
         * and will create it if doesn't exist.
         * Changing the state of an existing AI component
         * @param name
         */
        public void changeState(String name) {
                stateMap.put(name, stateMap.containsKey(name) ? stateMap.get(name) + 1 : 1);
        }

        public void registerState(AIState state) {

        }

}
