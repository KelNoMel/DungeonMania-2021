package dungeonmania;

import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.*;
import dungeonmania.entities.*;
import dungeonmania.entities.statics.*;
import dungeonmania.goals.*;
import dungeonmania.entities.spawners.MercenarySpawner;
import dungeonmania.entities.spawners.SpiderSpawner;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.*;

/**
 * Dungeon class describes all aspects of a DungeonMania game
 * Class Invariants:
 * No two Entities will have the same id
 */
public class Dungeon {

	private String dungeonId;
    private String dungeonName;
    private Gamemode gamemode;
    
    private EntityList entities = new EntityList();
    private List<AnimationQueue> animations = new ArrayList<AnimationQueue>();  
    
    private Goal dungeonGoal;
    
	////////////////////////////////////////////////////////////////////////////////
	///                         New Dungeon Construction                         ///
	////////////////////////////////////////////////////////////////////////////////
    
    private static String createId() {
        return UUID.randomUUID().toString();
    }
    
    public Dungeon(String id, String name, Gamemode gamemode) {
    	this.dungeonId = id;
    	this.dungeonName = name;
    	this.gamemode = gamemode;
    	this.dungeonGoal = new DefaultGoal(this);
    	Portal.clearPortalLinks();
    }
    
    public Dungeon(String dungeonName, String gamemode) throws JSONException {
        this.dungeonName = dungeonName;
    	this.gamemode = Gamemode.getGamemode(gamemode);
    	
    	JSONObject dungeonJSON = readDungeonJSON(dungeonName);
    	
    	Portal.clearPortalLinks();
        EntityFactory.loadEntities(dungeonJSON.getJSONArray("entities"), this, entities);        
        loadOther();
        
		dungeonGoal = loadGoalFromFile(dungeonJSON);
    	
    	dungeonId = Dungeon.createId();
    }
    
    private JSONObject readDungeonJSON(String dungeonName) throws IllegalArgumentException {
    	try {
    		return new JSONObject(
    				FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json")
    				);
    		
    	} catch (Exception e) {
    		throw new IllegalArgumentException("dungeonName is not a dungeon that exists");
    	}
    }
    
    private Goal loadGoalFromFile(JSONObject dungeonJSON) {
    	JSONObject goalInfo;
    	try {
    		goalInfo = dungeonJSON.getJSONObject("goal-condition");
    		return loadGoals(goalInfo);
    	} catch (JSONException e) {
    		return new DefaultGoal(this);
    	}
    }
    
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Dungeon Loading                             ///
	////////////////////////////////////////////////////////////////////////////////
    
    public Dungeon(File loadFile) {
    	JSONObject fileData;
    	try {
			fileData = new JSONObject(new String(Files.readAllBytes(loadFile.toPath())));
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to create the dungeon from the selected file");
		}
    	
    	dungeonName = fileData.getString("dungeon-name");
    	gamemode = Gamemode.getGamemode(fileData.getString("gamemode"));
    	Portal.clearPortalLinks();
    	EntityFactory.loadEntities(fileData.getJSONArray("entities"), this, entities);
    	entities.removeDeadEntities();
    	loadOther();
    	dungeonGoal = loadGoalFromFile(fileData);
    	dungeonId = fileData.getString("dungeon-id");
    }

	////////////////////////////////////////////////////////////////////////////////
	///                              JSON Extraction                             ///
	////////////////////////////////////////////////////////////////////////////////
    
	private void loadOther() {
    	// If no merc spawner, load in
    	if (entities.numEntitiesOfType(MercenarySpawner.class) == 0) {
    		Position p = getPlayer().getPosition();
    		EntityFactory.constructEntity(newEntityJSON(p.getX(), p.getY(), "mercenary_spawner"), this);
    	}
    	
    	if (entities.numEntitiesOfType(SpiderSpawner.class) == 0) {
    		EntityFactory.constructEntity(newEntityJSON(0, 0, "spider_spawner"), this);
    	}
    	
    	if (entities.numEntitiesOfType(SpiderSpawner.class) == 0) {
    		EntityFactory.constructEntity(newEntityJSON(0, 0, "hydra_spawner"), this);
    	}
    	
    	// Should be singleton??
    	if (entities.numEntitiesOfType(BattleResolver.class) == 0) {
    		EntityFactory.constructEntity(newEntityJSON(0, 0, "battle_resolver"), this);
    	}
    }
    
    private Goal loadGoals(JSONObject goalJSON) {
		JSONArray subgoals;
		
		switch(goalJSON.getString("goal")) {
			case "AND":
				subgoals = goalJSON.getJSONArray("subgoals");
				return new AndGoal(this, loadGoals(subgoals.getJSONObject(0)), loadGoals(subgoals.getJSONObject(1)));
			case "OR":
				subgoals = goalJSON.getJSONArray("subgoals");
				return new OrGoal(this, loadGoals(subgoals.getJSONObject(0)), loadGoals(subgoals.getJSONObject(1)));
			case "exit":
				return new ExitGoal(this);
			case "treasure":
				return new TreasureGoal(this);
			case "boulders":
				return new BoulderGoal(this);
			case "enemies":
				return new EnemiesGoal(this);
			case "default":
				return new DefaultGoal(this);
			default:
				throw new IllegalArgumentException("GoalJSON is invalid");
		}
	}
    
    private JSONObject newEntityJSON(int x, int y, String entType) {
    	JSONObject newEntity = new JSONObject();
    	newEntity.put("x", getPlayer().getPosition().getX());
    	newEntity.put("y", getPlayer().getPosition().getY());
    	newEntity.put("type", entType);
    	return newEntity;
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Dungeon Saving                              ///
	////////////////////////////////////////////////////////////////////////////////
    
    public void saveGame(File saveFile) {
		try {
			// Make sure the file exists and is ready to write to
			saveFile.delete();
			saveFile.createNewFile();
		} catch (IOException e) {
			return;
		}
		
		// Retrieve all data to save
		JSONObject saveData = new JSONObject();
		saveData.put("dungeon-id", dungeonId);
		saveData.put("dungeon-name", dungeonName);
		saveData.put("entities", entities.toJSON());
		saveData.put("gamemode", gamemode.asString());
		saveData.put("goal-condition", dungeonGoal.toJSON());
		
		try {
			// Write the file
			FileWriter writer = new FileWriter(saveFile);
			writer.write(saveData.toString(4));
			writer.close();
		} catch (IOException e) {
			return;
		}
	}
    
	////////////////////////////////////////////////////////////////////////////////
	///                           Dungeon State Change                           ///
	////////////////////////////////////////////////////////////////////////////////

    /**
     * Advance the game one tick into the future
     * @param movementDirection 
     * @param itemUsed 
     */
    public void tick(InputState inputState) {
    	animations.clear();
    	processInput(inputState);
    	updateGame();
    }
    
    private void processInput(InputState inputState) {
    	entities.processInput(inputState);
    }
    
    private void updateGame() {
    	entities.updateEntities();
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                             Dungeon Response                             ///
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Get the types of buildables possible
	 * @return list of strings for each type of buildable
	 */
	public List<String> buildableResponse() {
		return Buildable.response(getPlayer());
	}
    
    /**
     * Create a DungeonResponse for the current Dungeon
     * @return DungeonResponse describing the currennt state of the game
     */
    public DungeonResponse response() {
        Player p = getPlayer();
    	if (p == null) {
    		return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
    				new ArrayList<ItemResponse>(), new ArrayList<String>(), dungeonGoal.response());
    	}
        return new DungeonResponse(dungeonId, dungeonName, entityResponse(),
        itemResponse(), buildableResponse(), dungeonGoal.response(), animations);
    }
    
    /**
     * Create a list of EntityResponse for each entity on the map
     * @return list of EntityResponse for all entities
     */
    private List<EntityResponse> entityResponse() {
        return new ArrayList<EntityResponse>(entities.stream()
            .map(e -> e.response()).collect(Collectors.toList()));
    }
	
	/**
     * Create a list of ItemRespones for each item in the player's inventory
     * @return list of all ItemResponses for the inventory
     */
    private List<ItemResponse> itemResponse() {
        return getPlayer().getInventoryResponse();
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                            Entity Construction                           ///
	////////////////////////////////////////////////////////////////////////////////
   

	public void build(String buildable) {
		getPlayer().build(buildable);
	}

	////////////////////////////////////////////////////////////////////////////////
	///                              Helper Methods                              ///
	////////////////////////////////////////////////////////////////////////////////
	
	public void queueAnimation(AnimationQueue q) {
		animations.add(q);
	}
	
	public EntityList getEntities() {
    	return entities;
    }

    public List<Entity> getEntitiesInRadius(Position origin, Double radius) {
    	List<Entity> radEnts = new ArrayList<>();
    	for (Entity e : entities) {
    		if (Position.withinRange(origin, e.getPosition(), radius)) {
    			radEnts.add(e);
    		}
    	}
    	return radEnts;
    }
    
    public List<Entity> getEntitiesAtPosition(Position checkPosition) {
    	List<Entity> posEnts = new ArrayList<>();
    	for (Entity e : entities) {
    		if (e.getPosition().equals(checkPosition)) {
    			posEnts.add(e);
    		}
    	}
    	return posEnts;
    }
    
    public Entity getEntityFromId(String id) {
    	for (Entity e : entities) {
    		if (e.getId().equals(id)) {
    			return e;
    		}
    	}
    	return null;
    }
	
	/**
	 * Get the player
	 * Assumes only one player always exists and it is stored as the first 
	 * entity in entities
	 * @return player
	 */
	public Player getPlayer() {
		if (entities.get(0) instanceof Player) {
			return (Player)entities.get(0);			
		}
		return null;
	}
	
	public Gamemode getGamemode() {
		return gamemode;
	}
	
	/**
	 * Check if the player is in a position
	 * @param pos
	 * @return true if player is at the (x,y) location. False otherwise.
	 */
	public boolean isPlayerHere(Position pos) {
		return getPlayer().getPosition().equals(pos);
	}

	public void transferToInventory(Entity e) {
		e.toggleDisplay(false);
		entities.transferEntity(getPlayer().getInventory(), e);
	}

	public void transferToMap(Entity e) {
		e.toggleDisplay(true);
		getPlayer().getInventory().transferEntity(entities, e);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public class Bounds {
		private Position minBounds;
		private Position maxBounds;
		
		public Bounds(Position minBounds, Position maxBounds) {
			this.minBounds = minBounds;
			this.maxBounds = maxBounds;
		}
		
		public Position getMinBounds() {
			return minBounds;
		}
		
		public Position getMaxBounds() {
			return maxBounds;
		}
	}
	
	public Bounds getBounds() {
		Position startPos = entities.get(0).getPosition();
		int minX = startPos.getX();
		int maxX = startPos.getX();
		int minY = startPos.getY();
		int maxY = startPos.getY();
		
		for (Entity e : entities) {
			Position entPos = e.getPosition();
			int checkX = entPos.getX();
			int checkY = entPos.getY();

			
			if (checkX < minX) {
				minX = checkX;
			} else if (checkX > maxX) {
				maxX = checkX;
			}
			
			if (checkY < minY) {
				minY = checkY;
			} else if (checkY > maxY) {
				maxY = checkY;
			}
		}
		
		return new Bounds(new Position(minX, minY), new Position(maxX, maxY));
	}
	
	////////////////////////////////////////////////////////////////////////////////
	///                            Dungeon Generation                            ///
	////////////////////////////////////////////////////////////////////////////////

	public static Dungeon generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String inputMode) {
		Dungeon generatedDungeon = new Dungeon(Dungeon.createId(), "prim-dungeon", Gamemode.getGamemode(inputMode));

		// Loads array as per provided prims algorithm
		Position start = new Position(xStart, yStart);
		Position end = new Position(xEnd, yEnd);
		int width = 50;
		int height = 50;
		ArrayList<ArrayList<Boolean>> maze = randomisedPrims(width, height, start, end);
		
		// TODO Iterator pattern?
		// Add walls to map
		for (int x = 0; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				// If is a wall
				Position newEntityPos = new Position(x,y);
				if (!getCoord(maze, newEntityPos)) {
					// Add wall to map
					new Wall(generatedDungeon, newEntityPos);
				}
			}
		}
		
		// Add player to start
		new Player(generatedDungeon, start);

		// Add goal to end
		new Exit(generatedDungeon, end);
		
		// Generate spawners
		generatedDungeon.loadOther();
		
		// Make dungeon goal end
		generatedDungeon.dungeonGoal = new ExitGoal(generatedDungeon);
		
		return generatedDungeon;
	}
	
	private static ArrayList<ArrayList<Boolean>> randomisedPrims(int width, int height, Position start, Position end) {
		// let maze be a 2D array of booleans (of size width and height) default false
		// You should presume all game maps are 50 by 50.
		ArrayList<ArrayList<Boolean>> maze = new ArrayList<>();
		for (int i = 0; i <= width; i++) {
			maze.add(new ArrayList<>());
			for (int j = 0; j <= height; j++) {
				maze.get(i).add(false);
			}
		}
		
		// maze[start] = empty
		setCoord(maze, start, true);

		// let options be a list of positions
		List<Position> options;
		// add to options all NEIGHBOURS of 'start' not on boundary that are of distance 2 away and are walls
		options = neighboursNotBoundaryTwoAway(maze, start, false, width, height);
		
		// while options is not empty:
		while (options.size() > 0) {
			// let next = remove random from options
			Position next = options.remove(random(options.size()));

			// let neighbours = each neighbour of distance 2 from next not on boundary that are empty
			List<Position> neighbours = neighboursNotBoundaryTwoAway(maze, next, true, width, height);
			
			// if neighbours is not empty:
			if (neighbours.size() > 0) {
				// let neighbour = random from neighbours
				Position neighbour = neighbours.remove(random(neighbours.size()));
				
				// maze[ next ] = empty (i.e. true)
				setCoord(maze, next, true);
				// maze[ position inbetween next and neighbour ] = empty (i.e. true)
				setCoord(maze, positionInbetween(next, neighbour), true);
				// maze[ neighbour ] = empty (i.e. true)
				setCoord(maze, neighbour, true);
			}
			
			// add to options all neighbours of 'next' not on boundary that are of distance 2 away and are walls
			options.addAll(neighboursNotBoundaryTwoAway(maze, next, false, width, height));
		}
		
		// if maze[end] is a wall:
		// TODO edge case that is never fulfilled?
		
		/*if (getCoord(maze, end) == false) {
			// maze[end] = empty
			setCoord(maze, end, true);
			
			// let neighbours = neighbours not on boundary of distance 1 from maze[end]
			List<Position> neighbours = new ArrayList<>();
			for (Position p : end.getAdjacentPositions()) {
				// Is on boundary?
				if (onBoundary(p, width, height)) continue;
				neighbours.add(p);
			}
			// if there are no cells in neighbours that are empty:
			boolean noneEmpty = true;
			for (Position p : neighbours) {
				if (getCoord(maze, p)) noneEmpty = false;
			}
			
			if (noneEmpty) {
				// let's connect it to the grid
				// let neighbour = random from neighbours
				Position neighbour = neighbours.remove(random(neighbours.size()));
				// maze[neighbour] = empty
				setCoord(maze, neighbour, true);
			}
		}*/
		
		return maze;
	}
	
	private static List<Position> neighboursNotBoundaryTwoAway(ArrayList<ArrayList<Boolean>> maze, Position src, boolean getEmpty, int width, int height) {
		List<Position> neighbours = new ArrayList<>();
		// Get all two away
		for (Position p : src.getTwoAwayPositions()) {
			// Not on boundary
			if (onBoundary(p, width, height)) continue;
			
			// Is wall
			if (getCoord(maze, p) == getEmpty) neighbours.add(p); 
		}
		return neighbours;
	}
	
	private static Position positionInbetween(Position p1, Position p2) {
		 return new Position((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
	}
	
	// random in [0,max)
	private static int random(int max) {
		return (int) Math.floor(Math.random() * max);
	}
	
	public static boolean onBoundary(Position p, int width, int height) {
		int x = p.getX();
		if (x <= 0 || x >= width) {
			return true;
		}
		int y = p.getY();
		if (y <= 0 || y >= height) {
			return true;
		}
		return false;
	}
	
	private static boolean getCoord(ArrayList<ArrayList<Boolean>> maze, Position p) {
		return maze.get(p.getX()).get(p.getY());
	}
	
	private static void setCoord(ArrayList<ArrayList<Boolean>> maze, Position p, boolean setValue) {
		maze.get(p.getX()).set(p.getY(), setValue);
	}
	
}


