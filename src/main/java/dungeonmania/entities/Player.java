package dungeonmania.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.InputState;
import dungeonmania.Subject;
import dungeonmania.entities.buildable.BuildableFactory;
import dungeonmania.entities.statics.Boulder;
import dungeonmania.entities.statics.Wall;
import dungeonmania.components.Component;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.PlayerComponent;
import dungeonmania.components.battles.AttackTypeEnum;
import dungeonmania.components.battles.BattleComponent;
import dungeonmania.components.battles.Power;
import dungeonmania.components.battles.PowerUser;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	final private int maxHealth = 10;
	// private int health = maxHealth;
	final private int attackDamage = 10;
	
	public PlayerComponent playerComponent = new PlayerComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 
		new Power(maxHealth, maxHealth, attackDamage, 0, PowerUser.PLAYER, AttackTypeEnum.FISTS));

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

	public void build(String buildable) {
		inventory.add(BuildableFactory.build(buildable, getDungeon()));
	}

	public void addToInventory(Entity item) {
		inventory.add(item);
	}

	public void removeTypeFromInventory(String item) {
		for (Entity i : inventory) {
			if (i.getType().equals(item)) {
				inventory.remove(i);
				return;
			}
		}
	}

	public void removeFromInventory(Entity item) {
		inventory.remove(item);
	}

	public ArrayList<ItemResponse> getInventoryResponse() {
		return new ArrayList<ItemResponse>(inventory.stream()
        .map(e -> new ItemResponse(e.getId(), e.getType()))
        .collect(Collectors.toList()));
	}

	public HashMap<String,String> getUsedList() {
		return usedList;
	}

	// Used to set players health, currently used to restore full health on heal
	public int setHealth(int hp) {
		// health = hp;
		int newHealth = 0;
		for (Component c : getComponents()) {
			if (c instanceof BattleComponent) {
				BattleComponent battleComponent = (BattleComponent)c;
				battleComponent.setHealth(hp);
				newHealth = battleComponent.getHealth();
			}
		}
		return newHealth;
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
