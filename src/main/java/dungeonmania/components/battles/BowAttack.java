package dungeonmania.components.battles;

import dungeonmania.Subject;
import dungeonmania.entities.Player;

public class BowAttack implements Attack {
    public boolean attack(Subject sub, Power power) {
        // queue a new attack into the player's list of attacks for the current battle
        if (sub instanceof BattleComponent) {
            BattleComponent owner = (BattleComponent)sub;
            owner.addAttack(power);
            return true;
        }
        return false;
    }

    public AttackTypeEnum getAttackType() {
        return AttackTypeEnum.DOUBLE;
    }
}
