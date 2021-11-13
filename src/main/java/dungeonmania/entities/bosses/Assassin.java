package dungeonmania.entities.bosses;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.BattleComponent;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    public Assassin(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
        super(dungeon, position, entitySpecificData);
        battleComponent.setHealth(100);    
    }

    protected void inputEntity(InputState inputState) {
        List<Entity> entities = getDungeon().getEntitiesInRadius(getPosition(), 2.0);
        for (Entity e : entities) {
            if (e instanceof Player) {
                battleComponent.setAttackDamage(50);
            }
        }
    }
    
}
