package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.response.models.EntityResponse;

import java.util;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public abstract class Entity {
    private State state;
    private Position position;

    private String id;
    private String type;
    private boolean isInteractable;

    private Dungeon owningDungeon;
    private HashMap<String, Component> components;

    

    /**
     * Uses UUID generation and converts it into a string
     * for use
     * @return new unique entity id
     */
    private static String createId() {
        String id = UUID.randomUUID().toString();
        return id;
    }

    // TODO
    /**
     * Check if the type of the entity is interactable
     * @return boolean of if the type is interactable
     * @prereq attribute type has been set
     */
    private boolean interactableType() {
        return isInteractable;
    }

    /**
     * Entity constructor
     * @param type
     * @param position
     */
    public Entity(Dungeon dungeon, String type, Position position) {
        
        this.state = ACTIVE;
        this.position = position;
        this.id = createId();
        this.type = type;
        this.isInteractable = interactableType();
        this.dungeon = dungeon;
        this.components = new ArrayList<Component>(); 
    }

    public void update() {
        updateComponents();
        updateEntity();
    }

    public void updateComponents() {
        for (Component comp : components) {
            comp.update();
        }
    }

    public abstract void updateEntity();

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }
    
    /**
     * Creates an EntityResponse for this entity
     * @return EntityrResponse describing the entity
     */
    public EntityResponse response() {
        return new EntityResponse(id, type, position, isInteractable);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
