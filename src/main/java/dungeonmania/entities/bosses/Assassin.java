package dungeonmania.entities.bosses;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.response.models.EntityResponse;
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

	protected void inputEntity(InputState inputState) {
    }
}
