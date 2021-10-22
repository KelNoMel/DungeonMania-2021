package dungeonmania;

import java.util.ArrayList;

public class AIState {
        protected AIComponent owner;
        private static ArrayList<AIState> states; 

        /**
         * constructor for AI State
         * @param owner
         */
        public AIState(AIComponent owner) {
                this.owner = owner;
        }

        /**
         * State on entering game
         */
        public void onEnter() {
                // need enum state to complete
        }

        /**
         * State on exiting the game
         */
        public void onExit() {
                // need enum state
        }

        /**
         * updating AI states
         */
        public void update() {
                for (AIState state : states) {
                        state.update();
                    }
            
        }

        /**
         * registering states of a AI component
         * @param currentAIState
         */
        public static void registerState(AIState currentAIState) {
                states.add(currentAIState);
        }

        /**
         * remove state of a AI component
         * @param currentAIState
         */
        public static void removeState(AIState currentAIState) {
                states.remove(currentAIState);
        }

}
