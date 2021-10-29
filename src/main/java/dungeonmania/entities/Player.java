package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Wall;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	private int health = 10;
	private int attackDamage = 10;
	private List<Entity> inventoryList = new ArrayList<>();
	private List<Entity> deadInventory = new ArrayList<>();
	
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);

	public Player(Dungeon dungeon, Position position) {
		super(dungeon, "player", position, false);
	}
	
	protected void inputEntity(InputState inputState) {}

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

	public void addToInventory(Entity Item) {
		inventoryList.add(Item);
	}

	public void removeFromInventory(Entity Item) {
		inventoryList.remove(Item);
	}

	public ArrayList<ItemResponse> getInventory() {
		return new ArrayList<ItemResponse>(inventoryList.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
	}
}
