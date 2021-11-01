package dungeonmania.entities;

import dungeonmania.InputState;
import dungeonmania.components.Component;

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
	
	public int getScaledAttackDamage() {
		return (int) Math.ceil(health * attackDamage);
	}
	
	public int getHealth() { return health; }
	public void setHealth(int health) { this.health = health; }
	public int getAttackDamage() { return attackDamage; }
	public void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }
	
	public void dealDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			getEntity().setState(EntityState.DEAD);
		}
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
}
