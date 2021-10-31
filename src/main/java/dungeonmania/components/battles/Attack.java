package dungeonmania.components.battles;

import dungeonmania.Subject;
import dungeonmania.entities.Player;

public interface Attack {
    public boolean attack(Subject sub, Power power);
    public AttackTypeEnum getAttackType();
}
