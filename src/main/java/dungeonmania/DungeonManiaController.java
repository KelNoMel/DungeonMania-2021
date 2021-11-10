package dungeonmania;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

/**
 * Contains one method for each command you need to implement.
 */
public class DungeonManiaController {
    
	private Dungeon game;
    
    public DungeonManiaController() {
    	FileLoader.initialiseSaves();
    }

	////////////////////////////////////////////////////////////////////////////////
	///                              Game Management                             ///
	////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Creates a new game, where dungeonName is the name of the dungeon map (corresponding to
     * a JSON file stored in the model) and gameMode is one of "standard", "peaceful" or "hard".
     * @param dungeonName
     * @param gameMode
     * @return
     * @throws IllegalArgumentException If gameMode is not a valid game mode
     * @throws IllegalArgumentException If dungeonName is not a dungeon that exists
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
    	System.out.println(dungeonName);
    	game = new Dungeon(dungeonName, gameMode);
        return game.response();
    }
    
    private static File getSaveFile(String saveName) throws IllegalArgumentException {
		try {
			return FileLoader.getSavePath().resolve(saveName + ".json").toFile();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid save name");
		}
	}
    
    // TODO
    /**
     * Saves the current game state with the given ID.
     * @param saveName
     * @return
     * @throws IllegalArgumentException If id is not a valid game id
     */
    public DungeonResponse saveGame(String saveName) throws IllegalArgumentException {
		if (game == null) return null;
    	game.saveGame(getSaveFile(saveName));
    	return game.response();
    }

    /**
     * Loads the game with the given save name.
     * @param loadName
     * @return
     * @throws IllegalArgumentException If id is not a valid game id
     */
    public DungeonResponse loadGame(String loadName) throws IllegalArgumentException {
		game = new Dungeon(getSaveFile(loadName));
    	return game.response();
    }

    /**
     * Returns a list containing all the saved games that are currently stored
     * @return
     */
    public List<String> allGames() {
		try {
			return FileLoader.listSaves();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                             Game State Change                            ///
	////////////////////////////////////////////////////////////////////////////////
    
 // TODO
    /**
     * Ticks the game state. When a tick occurs:
     * 1. The player moves in the specified direction one square
     * 2. All enemies move respectively
     * 3. Any items which are used are 'actioned' and interact with the relevant entity
     * @param itemUsed
     * @param movementDirection
     * @return
     * @throws IllegalArgumentException If itemUsed is not one of bomb, invincibility_potion, invisibility_potion
     * @throws InvalidActionException If itemUsed is not in the player's inventory
     */
    public DungeonResponse tickActual(InputState inputState) throws IllegalArgumentException, InvalidActionException {
    	game.tick(inputState);
    	return game.response();
    }
    
    // TODO
    /**
     * Ticks the game state. When a tick occurs:
     * 1. The player moves in the specified direction one square
     * 2. All enemies move respectively
     * 3. Any items which are used are 'actioned' and interact with the relevant entity
     * @param interactID
     * @param movementDirection
     * @return
     * @throws IllegalArgumentException If itemUsed is not one of bomb, invincibility_potion, invisibility_potion
     * @throws InvalidActionException If itemUsed is not in the player's inventory
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
    	return tickActual(new InputState(itemUsed, movementDirection, null));
    }
    
    /**
     * Builds the given entity, where buildable is one of bow and shield.
     * @param buildable
     * @return
     * @throws IllegalArgumentException If buildable is not one of bow, shield
     * @throws InvalidActionException If the player does not have sufficient items to craft the buildable
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        game.build(buildable);
        return game.response();
    }
    
    // TODO
    /**
     * Interacts with a mercenary (where the character bribes the mercenary) or a zombie spawner, where
     * the character destroys the spawner.
     * @param entityId
     * @return
     * @throws IllegalArgumentException If entityId is not a valid entity ID
     * @throws InvalidActionException If the player is not cardinally adjacent to the given entity
     * @throws InvalidActionException If the player does not have any gold and attempts to bribe a mercenary
     * @throws InvalidActionException If the player does not have a weapon and attempts to destroy a spawner
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return tickActual(new InputState(null, null, entityId));
    }

    ////////////////////////////////////////////////////////////////////////////////
    ///                            Provided Functions                            ///
    ////////////////////////////////////////////////////////////////////////////////
    
    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("standard", "peaceful", "hard");
    }

    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    
}