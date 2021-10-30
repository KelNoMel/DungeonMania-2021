package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class EnemiesGoalTest {
    
    @Test
    public void enemiesGoalTest() {
        // DungeonManiaController mania = new DungeonManiaController();

        // DungeonResponse newGame = mania.newGame("floorswitch","Standard");
        // assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(0, 0), false), newGame));
        // assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "sword", new Position(1, 0), false), newGame));
        // assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "spider", new Position(2, 0), false), newGame));

        // // check state
        // assertFalse(ResponseHelp.goalComplete(newGame));
        
        // DungeonResponse movedResponse = mania.tick(null, Direction.RIGHT);

        // // Check that the player and boulder moved 
        // assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", new Position(1, 0), false), movedResponse));
        
        // // check state
        // assertTrue(ResponseHelp.goalComplete(movedResponse));

    }
}
