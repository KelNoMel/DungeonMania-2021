package dungeonmania.entities.bosses;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    public Assassin(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
        super(dungeon, position, entitySpecificData);    
    }
    
}
