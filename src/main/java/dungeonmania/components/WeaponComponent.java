package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.components.Component;

/**
 * A component containing the damage amount and type for a weapon
 */
public class WeaponComponent extends Component {
    private int damage;
    private AttackTypeEnum attackType;

    /**
     * 
     * @param owningEntity
     * @param updateOrder
     * @param damage
     * @param attackType
     * @throws Exception if owning entity does not have a BattleItemComponent
     * @pre owning entity has a BattleItemComponent
     */
    public WeaponComponent(Entity owningEntity, int updateOrder, int damage, AttackTypeEnum attackType) {
        super(owningEntity, updateOrder);
        try {
            if (owningEntity.getComponent(BattleItemComponent.class) == null) {
                throw new Exception("Incorrect usage: WeaponComponent requires an entity with a BattleItemComponent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.damage = damage;
        this.attackType = attackType;
    }

    public void processInput(InputState inputState) {}
    
    public void updateComponent() {} 

    public void useItem() {
       getBattleItem().useItem();
    }

    public boolean isBroken() {
        return getBattleItem().isBroken();
    }

    private BattleItemComponent getBattleItem() {
        return getEntity().getComponent(BattleItemComponent.class);
    }

    public int getDamage() { return damage; }
    public AttackTypeEnum getType() { return attackType; }
}
