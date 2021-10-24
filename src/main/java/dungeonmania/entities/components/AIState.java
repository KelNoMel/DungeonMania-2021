package dungeonmania.entities.components;

public abstract class AIState {
    protected AIComponent owner;

    public AIState(AIComponent owner) {
        this.owner = owner;
    }
    
    public abstract void update();
    
    public abstract void onEnter();
    public abstract void onExit();
    public abstract String getName();
}
