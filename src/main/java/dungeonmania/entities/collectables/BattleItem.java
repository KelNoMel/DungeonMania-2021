package dungeonmania.entities.collectables;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public abstract class BattleItem extends Entity {
    private int durability;
    
    public BattleItem(Dungeon dungeon, String type, Position position, boolean isInteractable, int durability, JSONObject entitySpecificData) {
        super(dungeon, type, position, isInteractable, entitySpecificData);
        this.durability = durability;
    }

    public void useItem() {
        durability--;
        if (durability <= 0) {
            setState(EntityState.DEAD);
        }
    }

}
