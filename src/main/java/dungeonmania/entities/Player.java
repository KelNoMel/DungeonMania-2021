package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Wall;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	private int health = 10;
	private int attackDamage = 10;
	private List<Entity> inventoryList;

	public Player(Dungeon dungeon, Position position) {
		super(dungeon, "player", position, false);
	}
	
	protected void inputEntity(InputState inputState) {
		Position moveLocation = getPosition().translateBy(inputState.getMovementDirection());
		
		List<Entity> moveEntities = dungeon.getEntitiesAtPosition(moveLocation);
		
		// Attempt to move if boulder in move location
		boolean boulderMoved = true;
		boolean isWall = false;
		for (Entity e : moveEntities) {
			if (e instanceof Boulder) {				
				boulderMoved = ((Boulder)e).moveBoulder(inputState.getMovementDirection());
				break;
			} else if (e instanceof Wall) {
				isWall = true;
				break;
			}
		}
		
		// Instead for boulder a collisionless raycast could be passed down the checking chain?
		
		// Only move if move space is not covered by a wall or an unmovable boulder
		if (!(isWall) && boulderMoved) {
			setPosition(getPosition().translateBy(inputState.getMovementDirection()));			
		}
	}

	protected void updateEntity() {

	}

	public void addToInventory(Entity Item) {
		inventoryList.add(Item);
	}

	public void removeFromInventory(Entity Item) {
		inventoryList.remove(Item);
	}

	public ArrayList<ItemResponse> getInventory() {
		return new ArrayList<ItemResponse>(inventory.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
	}

	// Used to subtract players health by a value, used when taking damage
	public int takeDamage(int dmg) {
		health = health - dmg;
		return health;
	}

	// Used to set players health, currently used to restore full health on heal
	public int setHealth(int hp) {
		health = hp;
		return health;
	}
}
