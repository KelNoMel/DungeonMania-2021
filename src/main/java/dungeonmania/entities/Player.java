package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.Subject;
import dungeonmania.entities.buildable.BuildableFactory;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Wall;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity implements Subject {
	
	private int health = 10;
	private int attackDamage = 10;
	private ArrayList<Entity> inventoryList = new ArrayList<>();
	private List<Entity> deadInventory = new ArrayList<>();

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

		// process input for each entity in the inventory
		for (Entity i : inventoryList) {
    		i.processInput(inputState);
    	}
	}

	protected void updateEntity() {
		for (Entity i : inventoryList) {
    		i.update();
    	}

		for (Entity i : inventoryList) {
			if (i.getState() == EntityState.DEAD || i.getState() == EntityState.ACTIVE) {
				deadInventory.add(i);
			}
		}
		inventoryList.removeAll(deadInventory);
    	deadInventory.clear();
	}

	public void build(String buildable) {
		inventoryList.add(BuildableFactory.build(buildable, dungeon));
	}

	public void addToInventory(Entity item) {
		inventoryList.add(item);
	}

	public void removeTypeFromInventory(String item) {
		for (Entity i : inventoryList) {
			if (i.getType().equals(item)) {
				inventoryList.remove(i);
				return;
			}
		}
	}

	public void removeFromInventory(Entity item) {
		inventoryList.remove(item);
	}

	public ArrayList<ItemResponse> inventoryResponse() {
		return new ArrayList<ItemResponse>(inventoryList.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
	}

	public ArrayList<Entity> getInventory() {
		return inventoryList;
	}
}
