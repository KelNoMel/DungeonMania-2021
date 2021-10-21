package dungeonmania;

public class AIState {
        protected AIComponent owner;
        
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

}
