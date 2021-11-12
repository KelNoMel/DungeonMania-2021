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
import dungeonmania.entities.buildable.BuildableFactory;
import dungeonmania.entities.buildable.Sceptre;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.PlayerComponent;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Player extends Entity {
	public final int MAX_HP = 100;
	public final int DMG = 10;

	public PlayerComponent playerComponent = new PlayerComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, MAX_HP, DMG);


	private EntityList inventory;

	// Hashmap that tracks which items are used in input tick
	// Key is itemId, and value is itemType
	// Can swap with deadInventory and make deadInventory a method only field?
	public HashMap<String, String> usedList = new HashMap<String, String>();

	// Player states include: normal, invisible, invincible
	public String status = "normal";

	public Player(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "player", position, false, EntityUpdateOrder.PLAYER, entitySpecificData);
		dungeon.getEntities().removeDeadEntities();
	}
	
	private List<Entity> getTypeInInventory(String entityType) {
		return new ArrayList<>(inventory.stream().filter(e->e.getType().equals(entityType)).collect(Collectors.toList()));
	}
	
	protected void inputEntity(InputState inputState) {
		// I put inventory usage ahead of entity interaction
		// in input order (it was getting cancelled by line 61 earlier)
		if (inputState.getItemUsed() != null) {
			inventory.processInput(inputState);
		}
		
		if (inputState.getInteractId() == null) return;
		Entity interactEntity = getDungeon().getEntityFromId(inputState.getInteractId());
		switch (interactEntity.getType()) {
			case "mercenary":
				Mercenary bribeMercenary = null;
				if ((bribeMercenary = findMercenary(getDungeon().getEntitiesInRadius(getPosition(), 2), interactEntity.getId())) == null) {
					throw new InvalidActionException("The player is not within range of a Mercenary!");
				}
				// do not use resources to bribe a mercenary that is already bribed/controlled
				if (BattleResolver.isAlly(bribeMercenary)) break;
				List<Entity> playerTreasure = getTypeInInventory("treasure");
				List<Entity> playerSunStone = getTypeInInventory("sun_stone");
				List<Entity> playerSceptre = getTypeInInventory("sceptre");
				if (playerTreasure.size() < 1 && playerSunStone.size() < 1 && playerSceptre.size() < 1) {
					throw new InvalidActionException("You do not have sufficient gold/sun stone/sceptre to bribe the Mercenary!");
				}
				// Bribe away!
				if (playerSunStone.size() > 0) {
					System.out.println("Mercanary has been bribed the sun stone");
					bribeMercenary.aiComponent.changeState("MercAlly");
				} else if (playerTreasure.size() > 0) {
					System.out.println("Mercanary has been bribed using gold");
					playerTreasure.get(0).setState(EntityState.DEAD);
					bribeMercenary.aiComponent.changeState("MercAlly");
				} else if (playerSceptre.size() > 0 ) {
					System.out.println("Mercanary is being controlled with a sceptre");
					bribeMercenary.aiComponent.temporaryChangeState("MercAlly", Sceptre.MINDCONTROL_TIME);
				}
				break;
		}
		
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
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {
		baseJSON.put("inventory", inventory.toJSON());
	}

	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) throws JSONException {
		inventory = new EntityList();
		if (entitySpecificData.has("inventory")) {
			EntityFactory.loadEntities(entitySpecificData.getJSONArray("inventory"), getDungeon(), inventory);
		}
	}
}
