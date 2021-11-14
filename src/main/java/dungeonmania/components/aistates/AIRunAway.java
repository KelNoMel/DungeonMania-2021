package dungeonmania.components.aistates;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.components.MoveComponent;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class AIRunAway extends AIState {

	private Entity enemy;
    private MoveComponent eMoveComponent;
	
	public AIRunAway(AIComponent owner, Entity enemy, MoveComponent eMoveComponent) {
		super(owner);
		this.enemy = enemy;
        this.eMoveComponent = eMoveComponent;
	}
	
	public void processInput(InputState inputState) {
		// Default movement, at least better than moving towards the player
        Direction enemyMoveDirection = Direction.NONE;
		
		List<Direction> options = weighOptions(enemy.getDungeon().getPlayer().getPosition(), enemy.getPosition());
		// Pick a random direction out of options list (They should all in theory equally move the enemy further away from player)
        // Note: Doesn't factor in swamps/make use of Djikstra's algo. KISS
        Random rand = new Random();
        while (options.size() > 0) {
            int randomPickIndex = rand.nextInt(options.size());
            Direction option = options.get(randomPickIndex);
            eMoveComponent.setMoveDirection(option);
            options.remove(randomPickIndex);
            // Check if this option is valid, choose it if so
            if (eMoveComponent.canIMove(enemy)) {
                break;
            }
        }
        enemyMoveDirection = Direction.NONE;
	}

    // Gets player location and returns directions that don't bring the enemy closer
    public List<Direction> weighOptions(Position player, Position enemy) {
        List<Direction> options = new ArrayList<Direction>();
        // Go left or right
        if (player.getX() > enemy.getX()) {
            options.add(Direction.LEFT);
        } else if (player.getX() < enemy.getX()) {
            options.add(Direction.RIGHT);
        } else {
            options.add(Direction.LEFT);
            options.add(Direction.RIGHT);
        }

        // Go up or down
        if (player.getY() > enemy.getY()) {
            options.add(Direction.UP);
        } else if (player.getY() < enemy.getY()){
            options.add(Direction.DOWN);
        } else {
            options.add(Direction.UP);
            options.add(Direction.DOWN);
        }

        return options;
    }

	public void updateState() {
		//System.out.println("Scared");
	}

	public void onEnter() {}

	public void onExit() {}

	public String getName() {
		return "enemyRunAway";
	}
}
