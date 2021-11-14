package dungeonmania.components.aistates;

import java.util.Random;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.util.Direction;

public class AIHydraHostile extends AIState {

    private Hydra hydra;
    static private final Random randomiser = new Random();

    public AIHydraHostile(AIComponent owner, Hydra hydra) {
        super(owner);
        this.hydra = hydra;
    }

    @Override
    public void processInput(InputState inputState) {
        Direction zombieMoveDirection = generateRandom();

        hydra.moveComponent.setMoveDirection(zombieMoveDirection);  
    }

    private static Direction generateRandom() {
        int pick = randomiser.nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

    @Override
    public void updateState() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        return "HydraHostile";
    }
    
}
