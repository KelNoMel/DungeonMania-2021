package dungeonmania.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.EntityFactory;
import dungeonmania.EntityList;
import dungeonmania.InputState;
import dungeonmania.entities.bosses.Assassin;
import dungeonmania.entities.buildable.BuildableFactory;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.PlayerComponent;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	
	public PlayerComponent playerComponent = new PlayerComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 100, 10);


	private EntityList inventory;

	// Hashmap that tracks which items are used in input tick
	// Key is itemId, and value is itemType
	// Can swap with deadInventory and make deadInventory a method only field?
	public HashMap<String, String> usedList = new HashMap<String, String>();

	// Player states include: Normal, invisible, invincible
	public String status = "normal";

	public Player(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "player", position, false, entitySpecificData);
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
				if ((bribeMercenary = findMercenary(getDungeon().getEntitiesInRadius(getPosition(), 2), interactEntity.getId())) == null) {
					throw new InvalidActionException("The player is not within range of a Mercenary!");
				}
				List<Entity> playerTreasure = getTypeInInventory("treasure");
				List<Entity> playerSunStone = getTypeInInventory("sun_stone");
				if (playerTreasure.size() < 1 && playerSunStone.size() < 1) {
					throw new InvalidActionException("You do not have sufficient gold/sun stone to bribe the Mercenary!");
				}
				// Bribe away!
				if (playerTreasure.size() > 1) {
					playerTreasure.get(0).setState(EntityState.DEAD);
				}
				if (playerSunStone.size() > 1) {
					playerSunStone.get(0).setState(EntityState.DEAD);
				}
				bribeMercenary.aiComponent.changeState("MercAlly");
				break;
			case "assassin":
				Assassin bribeAssassin = null;
				if ((bribeAssassin = findAssassin(getDungeon().getEntitiesInRadius(getPosition(), 2), interactEntity.getId())) == null) {
					throw new InvalidActionException("The player is not within range of an Assassin!");
				}
				List<Entity> playerTreasure1 = getTypeInInventory("treasure");
				List<Entity> playerOneRing = getTypeInInventory("the_one_ring");
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
	public void setHealth(int hp) {
		battleComponent.setHealth(hp);
	}

	public EntityList getInventory() {
		return inventory;
	}

	public String getStatus() {
		return status;
	}

	// Used to set player status: Normal, invincible, invisible
	public void setStatus(String state) {
		status = state;
	}


	public Mercenary findMercenary(List<Entity> entities, String mercenaryId) {
		for (Entity e : entities) {
			if (e instanceof Mercenary && mercenaryId.equals(e.getId())) {
				return (Mercenary) e;
			}
		}
		return null;
	}

	public Assassin findAssassin(List<Entity> entities, String AssassinId) {
		for (Entity e : entities) {
			if (e instanceof Assassin && AssassinId.equals(e.getId())) {
				return (Assassin) e;
			}
		}
		return null;
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("inventory", inventory.toJSON());
	}

	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) throws JSONException {
		if (entitySpecificData.has("inventory")) {
			inventory = EntityFactory.loadEntities(entitySpecificData.getJSONArray("inventory"), getDungeon());
		} else {
			inventory = new EntityList();
		}
	}
}
