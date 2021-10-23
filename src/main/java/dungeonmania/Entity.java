package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.response.models.EntityResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;


public abstract class Entity {
    private EntityState state;
    private Position position;

    private String id;
    private String type;
    private boolean isInteractable;

    private Dungeon owningDungeon;
    private List<Component> components = new ArrayList<Component>();

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
        
        this.state = EntityState.ACTIVE;
        this.position = position;
        this.id = createId();
        this.type = type;
        this.isInteractable = interactableType();
        owningDungeon = dungeon;
    }

    public void update() {
        updateComponents();
        updateEntity();
    }

    private void updateComponents() {
        for (Component comp : components) {
            comp.update();
        }
    }

    protected abstract void updateEntity();

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

	public static Entity getEntity(JSONObject ent) {
		Position pos = new Position(ent.getInt("x"), ent.getInt("y"));
        
		switch (ent.getString("type")) {
//			case "wall":
//				return new Wall();
			default:
				return null;
		}
		
//		return Entity.getEntity(this, ent.getString("type"), pos));
	}

}
