package dungeonmania.components.aistates;

import java.util.Random;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.components.MoveComponent;
import dungeonmania.util.Direction;

public class AIRandomHostile extends AIState {
    static private final Random randomiser = new Random();
    MoveComponent moveComponent;
    
    public AIRandomHostile(AIComponent aiComponent, MoveComponent moveComponent) {
        super(aiComponent);
        this.moveComponent = moveComponent; 
    }

    // move in random directions
    public void processInput(InputState inputState) {
        Direction zombieMoveDirection = generateRandom();

        moveComponent.setMoveDirection(zombieMoveDirection);
    }

    private static Direction generateRandom() {
        int pick = randomiser.nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

    public void updateState() {
        //System.out.println("Zombie Hostile");
    }

    public void onEnter() {
        
    }

    public void onExit() {
        
    }

    public String getName() {
        return "RandomHostile";
    }
    
}
