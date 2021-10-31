package dungeonmania.entities;

import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.Component;
import dungeonmania.response.models.EntityResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

/**
 * Entity base class representing most objects found in the game.
 * Class Invariants:
 * Each Entity will have type that is not null
 */
public abstract class Entity {
	
    private EntityState state;
    private Position position;

    private String id;
    private String type;
    private boolean isInteractable;

    private Dungeon dungeon;
    private boolean shouldDisplay = true;
    
    private List<Component> components = new ArrayList<Component>();

    public Entity(Dungeon dungeon, String type, Position position, boolean isInteractable) {
    	this.dungeon = dungeon;
    	this.state = EntityState.ACTIVE;
        this.position = position;
        this.id = createId();
        this.type = type;
        this.isInteractable = isInteractable;
        
        dungeon.addEntity(this);
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                        Entity Loading/Construction                       ///
	////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Uses UUID generation and converts it into a string
     * for use
     * @return new unique entity id
     */
    private static String createId() {
        String id = UUID.randomUUID().toString();
        return id;
    }

	////////////////////////////////////////////////////////////////////////////////
	///                            Entity State Change                           ///
	////////////////////////////////////////////////////////////////////////////////

    public void processInput(InputState inputState) {
    	for (Component comp : components) {
    		comp.processInput(inputState);
    	}
    	
    	inputEntity(inputState);
    }
    
    protected abstract void inputEntity(InputState inputState);
    
    public void update() {
        updateComponents();
        updateEntity();
     }
    
    private void updateComponents() {
    	// Update components in order!
    	for (Component comp : components) {
    		comp.updateComponent();
    	}
    }
    
    protected abstract void updateEntity();
    
	////////////////////////////////////////////////////////////////////////////////
	///                                Components                                ///
	////////////////////////////////////////////////////////////////////////////////

    public void addComponent(Component newComp) {
//        components.add(newComp.);
        // it could be faster I know but I'm lazy
    	int numComponents = components.size();
    	int insertPosition = 0;
    	for (insertPosition = 0; insertPosition < numComponents; insertPosition++) {
    		if (components.get(insertPosition).getUpdateOrder() >= newComp.getUpdateOrder()) {
    			break;
    		}
    		insertPosition++;
    	}
    	components.add(numComponents, newComp);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                              Entity Response                             ///
	////////////////////////////////////////////////////////////////////////////////
    
    public EntityState getState() { return state; }
    public void setState(EntityState s) { state = s; }
    public Position getPosition() { return position; }
    public void setPosition(Position p) { position = p.asLayer(position.getLayer()); } 
    public Dungeon getDungeon() { return dungeon; }
    public String getId() { return id; }
    public String getType() { return type; }
    public void toggleDisplay(boolean display) { this.shouldDisplay = display; }
    
    public JSONObject toJSON() {
    	JSONObject entityJSON = new JSONObject();
    	entityJSON.put("x", position.getX());
    	entityJSON.put("y", position.getY());
    	entityJSON.put("type", type);
    	addJSONEntitySpecific(entityJSON);
    	return entityJSON;
    }
    public abstract void addJSONEntitySpecific(JSONObject baseJSON);
    
    public boolean withinRange(Entity e, int distance) {
		return Math.abs(Position.distanceBetween(position, e.getPosition()) - distance) <= Position.epsilon;
	}
    
    /**
     * Creates an EntityResponse for this entity
     * @return EntityResponse describing the entity
     */
    public EntityResponse response() {
        if (shouldDisplay) {
        	return new EntityResponse(getId(), type, position, isInteractable);
        }
        return new EntityResponse(getId(), "blank", position, isInteractable);
    }

}
