package dungeonmania.entities;

import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.Component;
import dungeonmania.response.models.EntityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity {
    private EntityState state;
    private Position position;

    private String id;
    private String type;
    private boolean isInteractable;

    protected Dungeon dungeon;
    
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
    	for (Component c : components) {
    		c.processInput(inputState);
    	}
    	
    	inputEntity(inputState);
    }
    
    protected abstract void inputEntity(InputState inputState);
    
    public void update() {
        updateComponents();
        updateEntity();
    }
    
    private void updateComponents() {
    	for (Component comp : components) {
    		comp.updateComponent();
    	}
    }
    
    protected abstract void updateEntity();
    
	////////////////////////////////////////////////////////////////////////////////
	///                                Components                                ///
	////////////////////////////////////////////////////////////////////////////////

    public void addComponent(Component component) {
        components.add(component);
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
    
    
    /**
     * Creates an EntityResponse for this entity
     * @return EntityResponse describing the entity
     */
    public EntityResponse response() {
        return new EntityResponse(getId(), type, position, isInteractable);
    }

	public String getId() {
		return id;
	}
}
