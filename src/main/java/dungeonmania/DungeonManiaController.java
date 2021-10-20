package dungeonmania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

/**
 * Contains one method for each command you need to implement.
 */
public class DungeonManiaController {
    private Dungeon currentGame;
    private List<Dungeon> games;
    
    public DungeonManiaController() {
        this.games = new ArrayList<Dungeon>();
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
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
        Dungeon dgn = new Dungeon(dungeonName, gameMode);
        games.add(dgn);
        currentGame = dgn;
        return dgn.response();
    }
    
    // TODO
    /**
     * Saves the current game state with the given ID.
     * @param name
     * @return
     * @throws IllegalArgumentException If id is not a valid game id
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return currentGame.response();
    }

    /**
     * Loads the game with the given id.
     * @param name
     * @return
     * @throws IllegalArgumentException If id is not a valid game id
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        List<Dungeon> game = games.stream()
            .filter(d -> d.getName().equals(name)).collect(Collectors.toList());
        if (game.size() == 0) {
            throw new IllegalArgumentException("id is not a valid game id");
        } else {
            currentGame = game.get(0);
            return currentGame.response();
        }
    }

    /**
     * Returns a list containing all the saved games that are currently stored
     * @return
     */
    public List<String> allGames() {
        return new ArrayList<String>(games.stream()
            .map(g -> g.getName()).collect(Collectors.toList()));
    }

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
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return currentGame.response();
    }
    
    // TODO
    /**
     * Builds the given entity, where buildable is one of bow and shield.
     * @param entityId
     * @return
     * @throws IllegalArgumentException If buildable is not one of bow, shield
     * @throws InvalidActionException If the player does not have sufficient items to craft the buildable
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return currentGame.response();
    }

    // TODO
    /**
     * Interacts with a mercenary (where the character bribes the mercenary) or a zombie spawner, where
     * the character destroys the spawner.
     * @param buildable
     * @return
     * @throws IllegalArgumentException If entityId is not a valid entity ID
     * @throws InvalidActionException If the player is not cardinally adjacent to the given entity
     * @throws InvalidActionException If the player does not have any gold and attempts to bribe a mercenary
     * @throws InvalidActionException If the player does not have a weapon and attempts to destroy a spawner
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return currentGame.response();
    }
}