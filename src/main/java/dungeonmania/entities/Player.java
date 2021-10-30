package dungeonmania.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.InputState;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.PlayerComponent;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	private int health = 10;
	private int attackDamage = 10;
	
	public PlayerComponent playerComponent = new PlayerComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);

	private EntityList inventory = new EntityList();

	// Hashmap that tracks which items are used in input tick
	// Key is itemId, and value is itemType
	// Can swap with deadInventory and make deadInventory a method only field?
	public HashMap<String, String> usedList = new HashMap<String, String>();

	public Player(Dungeon dungeon, Position position) {
		super(dungeon, "player", position, false);
	}
	
	private List<Entity> getTypeInInventory(String entityType) {
		return new ArrayList<>(inventory.stream().filter(e->e.getType().equals(entityType)).collect(Collectors.toList()));
	}
	
	protected void inputEntity(InputState inputState) {
		if (inputState.getInteractId() == null) return;
		
		Entity interactEntity = getDungeon().getEntityFromId(inputState.getInteractId());
		switch (interactEntity.getType()) {
			case "mercenary":
				Mercenary bribeMercenary = null;
				if ((bribeMercenary = findMercenary(getDungeon().getEntitiesInRadius(getPosition(), 2))) == null) {
					throw new InvalidActionException("The player is not within range of a Mercenary!");
				}
				List<Entity> playerTreasure = getTypeInInventory("treasure");
				if (playerTreasure.size() < 1) {
					throw new InvalidActionException("You do not have sufficient gold to bribe the Mercenary!");
				}
				// Bribe away!
				playerTreasure.get(0).setState(EntityState.DEAD);
				bribeMercenary.aiComponent.changeState("MercAlly");
				break;
		}
		inventory.processInput(inputState);
	}

	protected void updateEntity() {
		inventory.updateEntities();
	}

	public void addToInventory(Entity item) {
		inventory.add(item);
	}

	public ArrayList<ItemResponse> getInventoryResponse() {
		return new ArrayList<ItemResponse>(inventory.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
	}

	public HashMap<String,String> getUsedList() {
		return usedList;
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

	public EntityList getInventory() {
		return inventory;
	}


	public Mercenary findMercenary(List<Entity> entities) {
		for (Entity e : entities) {
			if (e instanceof Mercenary) {
				return (Mercenary) e;
			}
		}
		return null;
	}
}
