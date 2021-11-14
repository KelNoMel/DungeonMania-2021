package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.testhelper.ResponseHelp;
import dungeonmania.util.Position;

public class PrimsTest {

	@Test
    public void testPrimsAlgorithm() {
        DungeonManiaController mania = new DungeonManiaController();
        
        for (int i = 0; i < 100; i++) {
        	Position randStart = randPos();
        	Position randEnd = randPos();
        	
        	// Generate new dungeon for testing
        	DungeonResponse response = mania.generateDungeon(
        		randStart.getX(), randStart.getY(), randEnd.getX(), randEnd.getY(),
        		"peaceful"
    		);
        	
        	// Check there is a player at the start and an exit at the end
        	assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", randStart, false), response));
        	assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "exit", randEnd, false), response));
        	
        	// Check you can get to the end
        	assertTrue(isPathBetweenPositions(response, randStart, randEnd));
        }
    }
	
	@Test
    public void testPrimsAlgorithmEdgeCase() {
        DungeonManiaController mania = new DungeonManiaController();
        
//        for (int i = 0; i < 100; i++) {
//        	Position randStart = randPos();
//        	Position randEnd = randPos();
//        	
//        	// Generate new dungeon for testing
//        	DungeonResponse response = mania.generateDungeon(
//        		randStart.getX(), randStart.getY(), randEnd.getX(), randEnd.getY(),
//        		"peaceful"
//    		);
//        	
//        	// Check there is a player at the start and an exit at the end
//        	assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "player", randStart, false), response));
//        	assertTrue(ResponseHelp.entityInDungeon(new EntityResponse("", "exit", randEnd, false), response));
//        	
//        	// Check you can get to the end
//        	assertTrue(isPathBetweenPositions(response, randStart, randEnd));
//        }
    }
	
	private boolean isPathBetweenPositions(DungeonResponse d, Position start, Position end) {
		if (start == end) return true;

		// Is this good testing technique lmao???
		List<EntityResponse> entities = d.getEntities();

		List<Boolean> visited = new ArrayList<>();
		for (int i = 0; i < 2601; i++) {
			visited.add(false);
		}
		
		visited.set(posToInt(start), true);
		
		Queue<Position> q = new LinkedList<>();
		
		q.add(start);
		while (q.size() > 0) {
			Position pos = q.remove();
			if (pos.getX() == end.getX() && pos.getY() == end.getY()) {
				return true;
			} else {
				for (Position p : pos.getCardinalPositions()) {
					if (!walkable(entities, p)) continue;
					if (visited.get(posToInt(p)) == true) continue;
					visited.set(posToInt(p), true);
					q.add(p);
				}
			}
		}
		
		return false;
	}
	
	private boolean walkable(List<EntityResponse> entities, Position p) {
		if (isWallAtPos(entities, p)) return false;
		if (p.getX() < 0 || p.getX()>50) return false;
		if (p.getY() < 0 || p.getY()>50) return false;
		return true;
	}
	
	private int posToInt(Position p) {
		return p.getX() + 51*p.getY();
	}
	
	private boolean isWallAtPos(List<EntityResponse> responses, Position p) {
		for (EntityResponse r : responses) {
			if (r.getPosition().equals(p) && r.getType() == "wall") {
				return true;
			}
		}
		return false;
	}

	private Position randPos() {
		int randX = randInt(1,49);
		int randY = randInt(1,49);
		// Ensure is odd
		if (randX % 2 == 0) randX--;
		if (randY % 2 == 0) randY--;
		
		return new Position(randX, randY);
	}

	private int randInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	/*
	private void prettyPrintEntityResponseArray(List<EntityResponse> ents) {
		List<EntityResponse> removeEnts = new ArrayList<>();
		for (int i = 0; i < 51; i++) {
			for (int j = 0; j < 51; j++) {
				boolean blank = true;
				for (EntityResponse e : ents) {
					if (e.getPosition().getX() == i && e.getPosition().getY() == j) {
						if (e.getType().equals("player")) {
							
							System.out.print(e.getType() + " ");
						} else {							
							System.out.print(e.getType() + "   ");
						}
						
						removeEnts.add(e);
						blank = false;
						break;
					}
				}
				
				if (blank) System.out.print("------ ");
				
				ents.removeAll(removeEnts);
				removeEnts.clear();
			}
			System.out.println();
		}
	}*/
}
