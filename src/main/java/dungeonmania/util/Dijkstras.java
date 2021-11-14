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

    /*
    function Dijkstras(grid, source):
    let dist be a Map<Position, Double>
    let prev be a Map<Position, Position>

    for each Position p in grid:
        dist[p] := infinity
        previous[p] := null
    dist[source] := 0

    let queue be a Queue<Position> of every position in grid
    while queue is not empty:
        u := next node in queue with the smallest dist
        for each cardinal neighbour v of u:
            if dist[u] + cost(u, v) < dist[v]:
                dist[v] := dist[u] + cost(u, v)
                previous[v] := u
    return previous
    */

    public Map<Position, Position> dijkstras(Position source) {

        List<Position> grid = new ArrayList<>();

        // get grid for entities 
        XCOORD: for (int i = source.getX() - radius; i <= source.getX() + radius; i++ ) {
            YCOORD: for (int j = source.getY() - radius; j <= source.getY() + radius; j++ ) {
                Position p = new Position (i, j);
                // do not add position to grid if there is a blockage i.e. wall
                List<Entity> entList = dungeon.getEntitiesAtPosition(p);
                for (Entity e: entList) {
                    if (e instanceof Wall) continue YCOORD;
                }
                grid.add(p);            
            }
        }
       
        Queue<Position> q = new PriorityQueue<>();    
        for (Position p : grid) {
            dist.put(p, Double.valueOf(Double.POSITIVE_INFINITY));
            prev.put(p, null);
            q.add(p);
        }
        dist.replace(new Position(source.getX(), source.getY()), Double.valueOf(0));

        while (!q.isEmpty()) {
            Position u = q.remove();
            if (u == null) continue;

            // for all neighnbour positions recalculate distance and requeue
            OUTER_LOOP: for (Position v : u.getCardinalAdjacentPositions()) { 
                // ensure it is still within range
                if (!Position.withinRange(v, source, (double)radius)) continue OUTER_LOOP;
                // ensure we're not adding walls
                for (Entity e : dungeon.getEntitiesAtPosition(v)) {
                    if (e instanceof Wall) continue OUTER_LOOP;
                }

                if (dist.get(u) + cost(u) < dist.get(v)) {
                    dist.put(v , dist.get(u) + cost(u)); 
                    prev.replace(v, u);
                }
            }

        }
        return prev;
    }

    /**
     * Find the next position to travel such that it is the shortest path to the given destination
     * @param source current position
     * @param dest destination position
     * @return 
     */

    public Position getNextPosition (Position source, Position dest) {
        dijkstras(source);
        if (getNextHelper(source, new Position(dest.getX(), dest.getY())) == null) return ent.getPosition();
        return getNextHelper(source, new Position(dest.getX(), dest.getY()));
    }

    private Position getNextHelper(Position source, Position dest) {
        if (prev.get(dest) == null) {
            return null;
        }
        if (Position.sameLocation(source, prev.get(dest))) return dest;
        //if (source.equals(prev.get(dest))) return dest;
        return getNextHelper(source, prev.get(dest));
    }

    // Returns ticks needed to move off current position
    // current time remaining || swamp tile time || 1 (for normal tiles)
    private double cost (Position src) { 
        for (Entity e : dungeon.getEntitiesAtPosition(src)) {
            if (e instanceof SwampTile ) {
                SwampTile st = (SwampTile) e;
                int timeRemaining = st.getTimeRemaining(ent);
                if (timeRemaining == -1) {
                    return st.getMovementFactor();
                } else {
                    // TODO else if timeRemaining == 0 return 1?
                    return timeRemaining;
                }
            }
        }        
        return 1; 
    }

       
}
