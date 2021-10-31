package dungeonmania.components.battles;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Observer;
import dungeonmania.Subject;
import dungeonmania.components.Component;
import dungeonmania.components.aistates.AIMercAlly;
import dungeonmania.Subject;
import dungeonmania.Observer;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.InputState;

/**
 * BattleComponent class dictates whether an player or enemy lives or dies. 
 * It handles battles, weapon usage, offence and defence.
 * Items are notified in a battle before enemies to prepare for an attack by
 * keeping the items at the front of the observers list and enemies at the back.
 */
public class BattleComponent extends Component implements Subject, Observer {

    private Power power;
    private ArrayList<Power> attacks = new ArrayList<>();
    private Power removeStatsBuffer = new Power(0, 0, 0, AttackTypeEnum.FISTS);

    public BattleComponent(Entity owningEntity, int updateOrder, Power power) {
        super(owningEntity, updateOrder);
        this.power = power;
    }

    ////////////////////////////////////////////////////////////////////////////////
	///                                 Subject                                  ///
	////////////////////////////////////////////////////////////////////////////////

    public void attach(Observer o) {
        if (!listObservers.contains(o)) {
            addToAttachedList(this);
            if (o instanceof BattleItemComponent) {
                attachItem(o);
            } else {
                listObservers.addLast(o);
            }
    	}
    }

    /**
     * Attach item and its effects to the player
     * @param o
     * @pre Observer is of type BattleItemComponent and caller is the player
     */
    private void attachItem(Observer o) {
        // items are notified in a battle before enemies to prepare for an attack
        listObservers.addFirst(o);
        if (o instanceof ArmourComponent) {
            // add the effects of the item
            ArmourComponent item = (ArmourComponent)o;
            item.addItemEffects(this);
        }
    }

    /**
     * Permanently add the effects of an item to the caller
     * @param addedItemPower
     */
    public void addItemEffects(Power addedItemPower) {
        power.add(addedItemPower);
    }
    
	// public void detach(Observer o)
        // pretty much done already
        // use case in player TODO
        // use case in enemies TODO
	
    /**
     * Notify all observers that a battle has started
     */
	public void notifyObservers() {
        // get the combined weapon damage for attacks into the attacks list  
        // then send out the beatings
        for (Observer observer : listObservers) {
            if (!(observer instanceof ArmourComponent)) {
                observer.updateObserver(this);
            }
        }
    }


    /**
     *  Add the item's power to the buffer to remove it from the player after 
     * the battle
     * @param removeItemPower
     */
    public void removeItemEffectsAfterBattle(Power removeItemPower) {
        removeStatsBuffer.add(removeItemPower);
    }
    
    
    public void removeItemEffects(Power removeItemPower) {
        power.remove(removeItemPower);
    }

    /**
     * 
     * @param itemPower
     */
	public void addAttack(Power itemPower) {
        if (attacks.size() == 1 && attacks.get(0).getAttackType().equals(AttackTypeEnum.FISTS)) {
            // replace fist attack
            attacks.remove(0);
        }
		attacks.add(itemPower);
	}

    ////////////////////////////////////////////////////////////////////////////////
	///                                 Observer                                 ///
	////////////////////////////////////////////////////////////////////////////////


    @Override
    public void updateObserver(Subject sub) {
        // TODO: check for invincibility potions

        BattleComponent battleComponent = (BattleComponent)sub;
        // player/ally/enemy: show up for a beating
        if (isPlayer(getEntity())) {
            // update items
            for (Observer observer : listObservers) {
                if (observer instanceof ArmourComponent) {
                    updateObserver(sub);
                }
            }
            // take damage
            takeDamage(battleComponent);
        } else if (isEnemy(getEntity())) {
            // take damage
            takeDamage(battleComponent);
        } else if (isMercenary(getEntity())) {
            if (isMercenaryAlly(getEntity())) {
                if (sub instanceof Player) {
                    // if ally being frenzied but not beaten : don't battle
                    // TODO: if merc and in range go into frenzy state
                } else {
                    // take damage
                    takeDamage(battleComponent);
                }
            } else {
                // take damage 
                takeDamage(battleComponent);
            }
        }


        // if player notify armour to decrease its durability after the battle
        removeItemEffects(removeStatsBuffer);
        resetRemoveStatsBuffer();
    }

    public void takeDamage(BattleComponent battleComponent) {
        if (!battleComponent.getPower().sendDamage(power, getEntity())) {
            detachAllSubjects();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////
	///                               Entitiy tick                               ///
	////////////////////////////////////////////////////////////////////////////////

    public void processInput(InputState inputState) {
        Entity currEntity = getEntity();
        for (Entity entity : currEntity.getDungeon().getEntities()) {
            if (entity.equals(currEntity)) continue;
            //List<Component> currComponents = entity.getComponents();
            for (Component component : entity.getComponents()) {
                if (component instanceof BattleComponent) {
                    // observe if not already doing so
                    if (isPlayer(currEntity)) {
                        // player notifies all enemies and mecenaries
                        attach((BattleComponent)component);
                    } else if (isEnemy(currEntity)) {
                        // enemy notifies player or allies
                        if (entity instanceof Player) {
                            attach((BattleComponent)component);
                        } else if (isMercenary(entity)) {
                            if (isMercenaryAlly(entity)) {
                                attach((BattleComponent)component);
                            }
                        }
                    } else if (isMercenary(currEntity)) {
                        // enemy / ally 
                        if (isMercenaryAlly(currEntity)) {
                            if (isEnemy(entity) || !isMercenaryAlly(entity)) {
                                attach((BattleComponent)component);
                            }
                        } else {
                            if (isPlayer(entity) || isMercenaryAlly(entity)) {
                                attach((BattleComponent)component);
                            }
                        }

                    }
                }
            }
        }
    }

    public boolean isPlayer(Entity entity) {
        return entity instanceof Player;
    }

    public boolean isEnemy(Entity entity) {
        return (getEntity() instanceof ZombieToast || getEntity() instanceof Spider);
    }

    public boolean isMercenary(Entity entity) {
        return (entity instanceof Mercenary);
    }

    public boolean isMercenaryAlly(Entity entity) {
        if (isMercenary(entity)) {
            Mercenary merc = (Mercenary)entity;
            if (merc.aiComponent.getAISate() instanceof AIMercAlly) {
                return true;
            }
        } 
        return false;
    }   
    
    public void updateComponent() {
        // TODO: check for invisibility potion

        // for all possible battles start a battle
        for (Observer observer : listObservers) {
            if (observer instanceof BattleComponent) {
                BattleComponent battleComponent = (BattleComponent)observer;
                if (battleComponent.getEntity().getPosition().equals(getEntity().getPosition())) {
                    // engage in battle
                    // add a default fists attack
                    attacks.add(power);
                    notifyObservers();
                }
            }
            removeItemEffects(removeStatsBuffer);
            resetRemoveStatsBuffer();
            attacks.clear();
        }
    }
    
    public void resetRemoveStatsBuffer() {
        removeStatsBuffer = new Power(0, 0, 0, AttackTypeEnum.FISTS);
    }

    ////////////////////////////////////////////////////////////////////////////////
	///                             Getters & Setters                            ///
	////////////////////////////////////////////////////////////////////////////////

    /**
     * Get the current attacks queued for the current battle
     * @return
     */
    public ArrayList<Power> getAttacks() {
        return attacks;
    }

    /**
     * Get the current power stats of the entity not including weapon damage
     * @return
     */
    public Power getPower() { return power; }

    public int getMaxHealth() { return power.getMaxHealth(); }
    public int getHealth() { return power.getHealth(); }
    public int getDamage() { return power.getDamage(); }
    public int getArmour() { return power.getArmour(); }
    public int getModifier() { return power.getModifier(); }
    public AttackTypeEnum getAttackType() { return power.getAttackType(); }
    public void setHealth(int hp) { power.setHealth(hp); }
}
