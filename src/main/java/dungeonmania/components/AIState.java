package dungeonmania.components;

import dungeonmania.InputState;

public abstract class AIState {
    protected AIComponent owner;

    public AIState(AIComponent owner) {
        this.owner = owner;
    }
    
    public abstract void processInput(InputState inputState);
    public abstract void updateState();
    
    public abstract void onEnter();
    public abstract void onExit();
    public abstract String getName();

}
