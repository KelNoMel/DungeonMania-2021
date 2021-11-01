package dungeonmania.components.aistates;

import java.util.Random;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.Dungeon.Bounds;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.util.Direction;

public class AIZombieHostile extends AIState {
    private ZombieToast zombie;
    static private final Random randomiser = new Random();

    public AIZombieHostile(AIComponent aiComponent, ZombieToast zombieToast) {
        super(aiComponent);
        this.zombie = zombieToast;
    }

    // move in random directions
    public void processInput(InputState inputState) {
        Direction zombieMoveDirection = generateRandom();

        zombie.moveComponent.setMoveDirection(zombieMoveDirection);
    }

    private static Direction generateRandom() {
        int pick = new Random().nextInt(Direction.values().length);
        return Direction.values()[pick];
    }

    public void updateState() {
        System.out.println("Zombie Hostile");
    }

    public void onEnter() {
        
    }

    public void onExit() {
        
    }

    public String getName() {
        return "ZombieHostile";
    }
    
}
