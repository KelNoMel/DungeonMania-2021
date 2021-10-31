package dungeonmania.components.battles;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Subject;
import dungeonmania.entities.Player;

public class NormalAttack implements Attack {
    public boolean attack(Subject sub, Power power) {
        if (sub instanceof BattleComponent) {
            BattleComponent owner = (BattleComponent)sub;
            // check no other normal type attacks are already being used for this
            // attack
            List<Power> otherNormalAttacks = owner.getAttacks().stream()
            .filter(e -> e.getAttackType().equals(AttackTypeEnum.NORMAL))
            .collect(Collectors.toList());
            if (otherNormalAttacks.size() == 0) {
                // queue a new attack into the player's list of attacks for the current battle
                owner.addAttack(power);
                return true;
            } 
        }
        return false;
    }

    public AttackTypeEnum getAttackType() {
        return AttackTypeEnum.NORMAL;
    }
}
