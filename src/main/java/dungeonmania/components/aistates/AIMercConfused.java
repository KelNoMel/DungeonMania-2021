package dungeonmania.components.aistates;

import java.util.Random;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Direction;

public class AIMercConfused extends AIState {
    private Mercenary merc;
    static private final Random randomiser = new Random();

    public AIMercConfused(AIComponent aiComponent, Mercenary mercenary) {
        super(aiComponent);
        this.merc = mercenary;
    }

    // move in random directions
    public void processInput(InputState inputState) {
        Direction mercMoveDirection = generateRandom();

        merc.moveComponent.setMoveDirection(mercMoveDirection);
    }

    private static Direction generateRandom() {
        int pick = randomiser.nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

    public void updateState() {
        //System.out.println("merc Hostile");
    }

    public void onEnter() {
        
    }

    public void onExit() {
        
    }

    public String getName() {
        return "MercConfused";
    }
    
}
