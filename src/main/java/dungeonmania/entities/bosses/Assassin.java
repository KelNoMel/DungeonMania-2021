package dungeonmania.entities.bosses;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    public Assassin(Dungeon dungeon, Position position) {
        super(dungeon, position);
        battleComponent.setHealth(150);    
    }

    protected void inputEntity(InputState inputState) {
        List<Entity> entities = getDungeon().getEntitiesInRadius(getPosition(), 2.0);
        for (Entity e : entities) {
            if (e instanceof Player) {
                battleComponent.setAttackDamage(15);
            }
        }
    }
    
}
