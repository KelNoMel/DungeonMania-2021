package dungeonmania.entities.bosses;

import dungeonmania.Dungeon;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    final static int attackDamage = 25;
	final static int maxHealth = 265;

    public Assassin(Dungeon dungeon, Position position) {
        super(dungeon, position);
        battleComponent.setHealth(maxHealth);    
        setType("assassin");
        battleComponent.setAttackDamage(attackDamage);
    }
}
