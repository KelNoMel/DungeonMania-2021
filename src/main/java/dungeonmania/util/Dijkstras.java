package dungeonmania.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.statics.SwampTile;
import dungeonmania.entities.statics.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dijkstras {

    private Entity ent;
    private Dungeon dungeon;
    private final int radius = 5;
    private Map<Position, Double> dist = new HashMap<Position, Double>();
    private Map<Position, Position> prev = new HashMap<Position, Position>();
    
    public Dijkstras(Dungeon dungeon, Entity ent) {
        this.ent = ent;
        this.dungeon = dungeon;
    }

    public Map<Position, Position> dijkstras(Position source) {
        List<Position> grid = new ArrayList<>();

        XCOORD: for (int i = -radius; i <= radius; i++ ) {
            YCOORD: for (int j = -radius; j <= radius; j++ ) {
                Position p = new Position (i, j);
                List<Entity> entList = dungeon.getEntitiesAtPosition(p);
                for (Entity e: entList) {
                    if (e instanceof Wall) continue YCOORD;
                }
                grid.add(p);            
            }
        }
       
        dist.put(source, Double.valueOf(0));
        Queue<Position> q = new PriorityQueue<>();    
        for (Position p : grid) {
            dist.put(p, Double.valueOf(Double.POSITIVE_INFINITY));
            prev.put(p, null);
            q.add(p);
        }

        while (!q.isEmpty()) {
            Position u = getSmallestDist(q, source);
            q.remove(u);
            OUTER_LOOP: for (Position v : u.getAdjacentPositions()) {
                // make sure we're not adding walls
                for (Entity e : dungeon.getEntitiesAtPosition(v)) {
                    if (e instanceof Wall) continue OUTER_LOOP;
                }
                if (dist.get(u) + cost(u) < dist.get(v)) {
                    dist.put(v , dist.get(u) + cost(u));
                    prev.put(v, u);
                }
            }

        }
        return prev;
    }

    private Position getSmallestDist (Queue<Position> q, Position src) {
        Position temp = null;
        int distance = 0;
        for (Position p: q) {
            if (dist.get(p) < Double.valueOf(distance)) // TODO: 
                temp = p;
        }
        return temp;
    }

    private double cost (Position src) { // check for swamp tiles 
        // return current time remaining || swamp tile time || 1 (for normal tiles)
        for (Entity e : dungeon.getEntitiesAtPosition(src)) {
            if (e instanceof SwampTile ) {
                SwampTile st = (SwampTile) e;
                int timeRemaining = st.getTimeRemaining(ent);
                if (timeRemaining == -1) {
                    return st.getMovementFactor();
                } else {
                    return timeRemaining;
                }
            }
        }        
        return 1; 
    }

       
}
