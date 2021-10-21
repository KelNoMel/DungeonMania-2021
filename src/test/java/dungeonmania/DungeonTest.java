package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;

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
        DungeonResponse response = mania.newGame("maze","Peaceful");
        //assertTrue(response);

        mania.newGame("boulders","Peaceful");
        //assertTrue();
        mania.newGame("advanced","Peaceful");
        //assertTrue();
    }

    // creat a new game where the map doesn't exist
    @Test
    public void testNewGameBadMap() {
        DungeonManiaController mania = new DungeonManiaController();
        mania.newGame("squid-game","Hard");
        
    }

    // attempt to create a new game where the map already exists

    // attempt to create a new game using an invalid gameMode

    
    /**
     * @saveGame()
     * DungeonResponse saveGame(String id)
     * throws IllegalArgumentException
     */
    // save a game

    // save a game using the same name again

    // save multiple games

    // attempt to save a game before starting any games

    /**
     * @loadGame()
     * DungeonResponse loadGame(String id) 
     * throws IllegalArgumentException
     */
    // load a previous game

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
