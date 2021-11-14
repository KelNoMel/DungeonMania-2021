package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

import org.json.JSONObject;

public class BattleComponent extends Component {

	private int maxHealth;
	private int health;
	private int attackDamage;

	public BattleComponent(Entity owningEntity, int updateOrder, int health, int attackDamage) {
		super(owningEntity, updateOrder);
		this.maxHealth = health;
		this.health = health;
		this.attackDamage = attackDamage;
	}
	
	public void processInput(InputState inputState) {}
	public void updateComponent() {}
	
	// Algorithm for damage given scaled by health
	public int getScaledAttackDamage(int weaponDamage) {
		return (int) Math.ceil(health * (attackDamage + weaponDamage));
	}

	public int getScaledAttackDamage() {
		return getScaledAttackDamage(0);
	}

	public void increaseDamge(int addedDamage) { attackDamage += addedDamage; }
	public int getHealth() { return health; }
	public void setHealth(int health) { this.health = health; }
	public int getAttackDamage() { return attackDamage; }
	public void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }
	
	// Gives damage and kills entities if hp goes to zero
	public void dealDamage(int damage) {
		health -= damage;
	}

	// Heals hp
	public void heal(int negDamage) {
		health += negDamage;
	}

	public boolean isAlive() {
		if (getEntity().getState() == EntityState.ACTIVE) {
			return true;
		}
		return false;
	}
	
	public String getHealthAsString() {
		return String.format("%.1f", (float)health / (float)maxHealth);
	}

	public void loadJSONComponentSpecific(JSONObject entityData) {
		if (entityData.has("maxHealth")) {
    		maxHealth = entityData.getInt("maxHealth");
    	}
		if (entityData.has("health")) {
    		health = entityData.getInt("health");
    	}
		if (entityData.has("attackDamage")) {
    		attackDamage = entityData.getInt("attackDamage");
    	}
	}
	
	public void addJSONComponentSpecific(JSONObject entityJSON) {
		entityJSON.put("maxHealth", maxHealth);
		entityJSON.put("health", health);
		entityJSON.put("attackDamage", attackDamage);
	}
}
