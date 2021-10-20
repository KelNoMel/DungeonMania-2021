package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.response.models.EntityResponse;


public class Entity {
    static private Integer lastId = 0;
    
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    /**
     * Creates a new id by adding 1 to the integer value of the last id created
     * Note: This may generate used ids if persistance is added. Use UUID's in 
     *       that case.
     * @return new unique entity id
     */
    private static String createId() {
        return String.valueOf(++lastId); 
    }

    // TODO: interactableType() this is dummy code
    /**
     * Check if the type of the entity is interactable
     * @return boolean of if the type is interactable
     * @prereq attribute type has been set
     */
    private boolean interactableType() {
        return true;
    }

    /**
     * Entity constructor
     * @param type
     * @param position
     */
    public Entity(String type, Position position) {
        this.id = createId();
        this.position = position;
        this.type = type;
        this.isInteractable = interactableType();
    }

    /**
     * Creates an EntityrResponse for this entity
     * @return EntityrResponse describing the entity
     */
    public EntityResponse response() {
        return new EntityResponse(id, type, position, isInteractable);
    }


}
