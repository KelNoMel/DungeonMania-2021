package dungeonmania.components.battles;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

public class Power {
    private int health;
    private int damage;
    private int armour;
    private int userModifier;
    private int maxHealth;
    private AttackTypeEnum attackType;

    public Power(int maxHealth, int health, int damage, int armour, int userModifier, AttackTypeEnum attackType) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.damage = damage;
        this.armour = armour;
        this.userModifier = userModifier;
        this.attackType = attackType;
    }

    public Power(int maxHealth, int health, int damage, int armour, PowerUser user, AttackTypeEnum attackType) {
        this(maxHealth, health, damage, armour, user.getModifier(), attackType);
    }

    /**
     * Construct an item that will not use the damage formula itself
     * @param health
     * @param damage
     * @param armour
     */
    public Power(int health, int damage, int armour, AttackTypeEnum attackType) {
        this(health, health, damage, armour, 1, attackType);
    }

    /**
     * Send damage from caller to other entity
     * @param receiver
     * @param receiverEntity
     * @return true if they are alive, false otherwise
     */
    public boolean sendDamage(Power receiver, Entity receiverEntity) { 
        receiver.setHealth(receiver.getHealth() - ((health * damage) / (receiver.getModifier() + receiver.getArmour())));
        if (receiver.getHealth() <= 0) {
            receiverEntity.setState(EntityState.DEAD);
            return false;
        }
        return true;
    }

    /**
     * Add the power of an item to the current power. Overwrite this power
     * with the item attack type.
     * @param addedItemPower
     */
    public void add(Power addedItemPower) {
        this.health += addedItemPower.getHealth();
        this.damage += addedItemPower.getDamage();
        this.armour += addedItemPower.getArmour();
        this.attackType = addedItemPower.getAttackType();
    }

    /**
     * Add to the power stats used for an attack to the basePower (not the maxHealth or user modifier)
     * Note the addedItemPower will overwrite the basePower attackType
     * @param basePower
     * @param addedItemPower
     * @return new aggregated Power
     */
    public static Power concact(Power basePower, Power addedItemPower) {
        int health = basePower.getHealth() + addedItemPower.getHealth();
        int damage = basePower.getDamage() + addedItemPower.getDamage();
        int armour = basePower.getArmour() + addedItemPower.getArmour();
        return new Power(basePower.getMaxHealth(), health, damage, armour, basePower.getModifier(), addedItemPower.getAttackType());
    }

    /**
     * Remove the added damage or armour item
     * @param removeItemPower
     * @pre the item effects should have been already added beforehand to the character
     */
    public void remove(Power removeItemPower) {
        damage -= removeItemPower.getDamage();
        armour -= removeItemPower.getArmour();
    }

    public int getMaxHealth() { return maxHealth; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getArmour() { return armour; }
    public int getModifier() { return userModifier; }
    public AttackTypeEnum getAttackType() { return attackType; }
    public void setHealth(int hp) { health = hp; }

}
