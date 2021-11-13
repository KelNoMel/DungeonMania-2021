package dungeonmania.entities.statics;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.entities.moving.Spider;
import dungeonmania.entities.moving.ZombieToast;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    private int movement_factor;
    private Map<Entity, Integer> entTimeRemainingAtLocation;
    
	public SwampTile(Dungeon dungeon, Position position, JSONObject entitySpecificData) {       
		super(dungeon, "swamp_tile", position, false, entitySpecificData);
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

	protected void updateEntity() {
        for (Map.Entry<Entity, Integer> pair : entTimeRemainingAtLocation.entrySet()) {
            pair.setValue(pair.getValue() - 1);
            // at 0, move // TODO: 
            if (pair.getValue() < 0) { // has left
                entTimeRemainingAtLocation.remove(pair.getKey());
            }
        }   
    }

	protected void inputEntity(InputState inputState) {}

	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
    
}
