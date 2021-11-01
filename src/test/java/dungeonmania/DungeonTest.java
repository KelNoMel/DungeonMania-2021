package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static dungeonmania.testhelper.OtherHelp.removeSaves;

import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Direction;

public class DungeonTest {
    /**
     * @newGame()
     * DungeonResponse newGame(String dungeonName, String gameMode)
     * throws IllegalArgumentException
     */
    // create a new game
    @Test
    public void testNewGame() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("maze","Peaceful");
        //assertTrue();
        //assertDoesNotThrow(() -> ));
        
        mania.newGame("boulders","Peaceful");
        //assertTrue();
        mania.newGame("advanced","Peaceful");
        //assertTrue();
    }

    // create a new game where the map doesn't exist
    @Test
    public void testNewGameBadMap() {
        DungeonManiaController mania = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> {
            mania.newGame("squid-game","Hard");
        });
        removeSaves();
    }

    // attempt to create a new game where the map already exists

    // attempt to create a new game using an invalid gameMode

    
    /**
     * @saveGame()
     * DungeonResponse saveGame(String id)
     * throws IllegalArgumentException
     */
    // No errors thrown by basic save
    @Test
    public void testSaveNoError() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        assertDoesNotThrow(()->mania.saveGame("SaveFile"));
        removeSaves();
    }
    
    // save a game using the same name again
    @Test
    public void testSameName() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        mania.saveGame("SaveFile");
        assertDoesNotThrow(()->mania.saveGame("SaveFile"));
        removeSaves();
    }
    
    // save multiple games
    @Test
    public void testMultipleSaves() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        assertDoesNotThrow(()->mania.saveGame("SaveFile1"));
        mania.newGame("portals","Hard");
        assertDoesNotThrow(()->mania.saveGame("SaveFile2"));
        mania.newGame("maze","Standard");
        assertDoesNotThrow(()->mania.saveGame("SaveFile3"));
        removeSaves();
    }

    // attempt to save a game before starting any games
    @Test
    public void testNoGame() {
        DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse d = mania.saveGame("SaveFile");
        assertNull(d);
        removeSaves();
    }

    /**
     * @loadGame()
     * DungeonResponse loadGame(String id) 
     * throws IllegalArgumentException
     */
    // load a previous game
    @Test
    public void testLoadGameBasic() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("maze","Peaceful");
        DungeonResponse saveResponse = mania.saveGame("SaveFile");
        DungeonResponse loadResponse = mania.loadGame("SaveFile");
        assertTrue(ResponseHelp.dungeonEqual(saveResponse, loadResponse));
        removeSaves();
    }
    
    @Test
    public void testLoadGameABitMoreComplex() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        DungeonResponse saveResponse = mania.saveGame("SaveFile");
        DungeonResponse loadResponse = mania.loadGame("SaveFile");
        assertTrue(ResponseHelp.dungeonEqual(saveResponse, loadResponse));
        removeSaves();
    }
    
    @Test
    public void testLoadGameALOTMORECOMPLEX() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("advanced","Peaceful");
        
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.DOWN);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.RIGHT);
        mania.tick(null, Direction.DOWN);
        
        DungeonResponse saveResponse = mania.saveGame("SaveFile");
        DungeonResponse loadResponse = mania.loadGame("SaveFile");
        assertTrue(ResponseHelp.dungeonEqual(saveResponse, loadResponse));
        removeSaves();
    }
    
    @Test
    public void testLotsOfStuff() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("alotofstuff","Peaceful");
        DungeonResponse saveResponse = mania.saveGame("SaveFile");
        DungeonResponse loadResponse = mania.loadGame("SaveFile");
        assertTrue(ResponseHelp.dungeonEqual(saveResponse, loadResponse));
        removeSaves();
    }
    
    @Test
    public void testLoadGameInvalid() {
        DungeonManiaController mania = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, ()->mania.loadGame("yeet"));
        removeSaves();
    }
    
    // load the current game

    // attempt to load a game that doesn't exist

    
    /**
     * @allGames()
     * List<String> allGames()
     */
    // get an empty list

    // get a list of multiple games
    

    /**
     * @tick()
     * DungeonResponse tick(String itemUsedId, Direction movementDirection)
     * throws IllegalArgumentException, InvalidActionException
     */
    // move player
    @Test
    public void testInteract() {
        // create game
        DungeonManiaController mania = new DungeonManiaController();
        DungeonResponse response = mania.newGame("maze","Peaceful");
        // find player
        EntityResponse res1 = ResponseHelp.getEntityOfType(response, "player");
        response = mania.tick(null, Direction.DOWN);
        // find player again after tick
        EntityResponse res2 = ResponseHelp.getEntityOfType(response, "player");
        assertEquals(res1.getPosition().getY() + 1, res2.getPosition().getY());
        // check if player is still in the same position after not moving
        response = mania.tick(null, Direction.NONE);
        EntityResponse res3 = ResponseHelp.getEntity(response, res2);
        assertNotEquals(null, res3);
    }

    // use an item and move
    

    // attempt to use an item that doesn't exist

    // use an item multiple times


    /**
     * @build()
     * DungeonResponse build(String buildable)
     * throws IllegalArgumentException, InvalidActionException
     */
    // build a buildable

    // attempt to build a buildable without sufficient items
    
    // build a buildable that doesn't exist

    // build a buildable multiple times

    // resources are drained by building


    /**
     * @interact()
     * DungeonResponse interact(String entityId)
     * throws IllegalArgumentException, InvalidActionException
     */
    // interact with an entity

    // attempt to interact with an entity that doesn't exist

    // attempt to interact with an entity that a player is not cardinally
    // adjacent to

    // attempt to bribe a mercenary with insufficient gold

    // attempt to destroy a spawner without a weapon


}
