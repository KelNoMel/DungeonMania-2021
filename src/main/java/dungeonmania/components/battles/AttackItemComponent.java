package dungeonmania.components.battles;

import dungeonmania.entities.Entity;
import dungeonmania.Subject;
import dungeonmania.InputState;
import dungeonmania.components.CollectableState;
import dungeonmania.components.battles.Attack;


public class AttackItemComponent extends BattleItemComponent {
    private Attack attackMethod;
    private int damage;
    
    public AttackItemComponent(Entity owningEntity, int updateOrder, int durability, int damage, Attack attackMethod) {
        super(owningEntity, updateOrder, durability);
        this.damage = damage;
        this.attackMethod = attackMethod;
    }

    // public void updateObserver(Subject sub) {

    // }
    
    public void processInput(InputState inputState) {}
    
    public void updateComponent() {}

    protected void removeItemEffects(Subject sub) {}

    protected void battle(Subject sub) {
        if(attackMethod.attack(sub, 
            new Power(0, damage, 0, attackMethod.getAttackType()))) {
            durability--;
        }
    }
}
