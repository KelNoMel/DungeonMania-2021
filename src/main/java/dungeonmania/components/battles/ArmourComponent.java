package dungeonmania.components.battles;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.Subject;

import dungeonmania.InputState;
import dungeonmania.components.CollectableState;



public class ArmourComponent extends BattleItemComponent {
    private int armour;
    
    public ArmourComponent(Entity owningEntity, int updateOrder, int durability, int armour) {
        super(owningEntity, updateOrder, durability);
        this.armour = armour;
    }

    // public void updateObserver(Subject sub) {
        
    // }
    
    public void processInput(InputState inputState) {}
    
    public void updateComponent() {}

    protected void battle(Subject sub) {
        durability--;
        if (durability <= 0) {
            // remove this after the battle is over
            removeItem(sub);
            getEntity().setState(EntityState.DEAD);
        }
    }

    protected void removeItemEffects(Subject sub) {
        if (sub instanceof BattleComponent) {
            BattleComponent owner = (BattleComponent)sub;
            owner.removeItemEffectsAfterBattle(new Power(0, 0, armour, AttackTypeEnum.FISTS));
        }
    }

    public void addItemEffects(Subject sub) {
        if (sub instanceof BattleComponent) {
            BattleComponent owner = (BattleComponent)sub;
            owner.addItemEffects(new Power(0, 0, armour, AttackTypeEnum.FISTS));
        }
    }

}

