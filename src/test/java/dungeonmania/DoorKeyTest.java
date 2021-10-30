package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectables.Key;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DoorKeyTest {
    
    // Test to see if key is added to inventory
    @Test
    public void testKey() {
        Dungeon mania = new DungeonManiaController();
        mania.newGame("boulders","Peaceful");

        Key key1 = new Key(mania, 100, "Key1");


    }
}
