package dungeonmania.entities.statics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    private int movement_factor;
    private Map<Entity, Integer> entTimeRemainingAtLocation;

	public SwampTile(Dungeon dungeon, Position position, JSONObject entitySpecificData) {       
		super(dungeon, "swamp_tile", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
        this.movement_factor = entitySpecificData.getInt("movement_factor");
        this.entTimeRemainingAtLocation = new HashMap<>();
        for (Entity e : dungeon.getEntitiesAtPosition(position)) {
            if (e instanceof Player || e instanceof Mercenary || e instanceof Spider || e instanceof ZombieToast ) {
            // TODO: 
            // if (e instanceof Player || e instanceof Mercenary || e instanceof Spider || e instanceOf ZombieToast || e instanceof Assassion || e instanceOf Hydra ) {
                entTimeRemainingAtLocation.put(e, movement_factor);
            }
        }
	}

    public int getMovementFactor() {
        return movement_factor;
    }

    public int getTimeRemaining( Entity ent) {
        if (entTimeRemainingAtLocation.get(ent) == null) return -1;
        return entTimeRemainingAtLocation.get(ent);
    }

    /**
     * Checks if entity is slowed by swamp tile
     * 
     * @param mover entity
     * @return true if entity still has time remaining on this tile
     */
    public boolean getMover(Entity mover) {
        for (Map.Entry<Entity, Integer> pair : entTimeRemainingAtLocation.entrySet()) {
            if (pair.getKey().equals(mover) && pair.getValue() > 0) return true; 
        }
        return false;
    }

	protected void updateEntity() {
        // add entities to list
        for (Entity e : super.getDungeon().getEntitiesAtPosition(super.getPosition())) {
            if (e instanceof Player || e instanceof Mercenary || e instanceof Spider || e instanceof ZombieToast ) {
            // TODO: 
            // if (e instanceof Player || e instanceof Mercenary || e instanceof Spider || e instanceOf ZombieToast || e instanceof Assassion || e instanceOf Hydra ) {
                Integer timeRemaining = entTimeRemainingAtLocation.get(e);
                if (timeRemaining == null) {
                    entTimeRemainingAtLocation.put(e, movement_factor);
                } else {
                    entTimeRemainingAtLocation.replace(e, timeRemaining, timeRemaining - 1);
                }                
            }
        }

        // check if entities have left
        List<Entity> entityAtTile = super.getDungeon().getEntitiesAtPosition(super.getPosition());
        for (Map.Entry<Entity, Integer> pair : entTimeRemainingAtLocation.entrySet()) {
            if (entityAtTile.indexOf(pair.getKey()) == -1) { // does not exist
                entTimeRemainingAtLocation.remove(pair.getKey());
            }
        }
           
    }

	protected void inputEntity(InputState inputState) {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
    
}
