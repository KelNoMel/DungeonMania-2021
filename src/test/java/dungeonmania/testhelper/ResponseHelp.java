package dungeonmania.testhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.testhelper.*;

public class ResponseHelp {    
    /**
     * Compare EntityResponse ignoring layer and id
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean entityEqual(
        EntityResponse res1, EntityResponse res2) {
        if (res1.isInteractable() != res2.isInteractable()) {
            return false;
        } else if (!res1.getType().equals(res2.getType())) {
            return false;
        } else if (res1.getPosition().getX() != res2.getPosition().getX()) {
            return false;
        } else if (res1.getPosition().getY() != res2.getPosition().getY()) {
            return false;
        }
        return true;
    }
    
    /**
     * Compare EntityResponse ignoring id
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean entityEqualWithLayer(
        EntityResponse res1, EntityResponse res2) {
        if (!res1.getPosition().equals(res2.getPosition())) {
            return false;
        }
        return entityEqual(res1, res2);
    }

    /**
     * Compare EntityResponse ignoring layer
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean entityEqualWithId(
        EntityResponse res1, EntityResponse res2) {
        if (!res1.getId().equals(res2.getId())) {
            return false;
        }
        return entityEqual(res1, res2);
    }

    /**
     * Compare EntityResponse
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean entityEqualWithLayerAndId(
        EntityResponse res1, EntityResponse res2) {
        if (!res1.getId().equals(res2.getId())) {
            return false;
        }
        return entityEqualWithLayer(res1, res2);
    }

    /**
     * Compare ItemResponse ignoring id
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean itemEqual(
        ItemResponse res1, ItemResponse res2) {
        if (!res1.getType().equals(res2.getType())) {
            return false;
        }
        return true;
    }

    /**
     * Compare ItemResponse
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean itemEqualWithId (
        ItemResponse res1, ItemResponse res2) {
        if (!res1.getType().equals(res2.getType())) {
            return false;
        } else if (!res1.getId().equals(res2.getId())) {
            return false;
        }
        return true;
    }
    
    /**
     * Compare two DungeonResponse ignoring animations, id, entity layers
     *  and ordering of lists
     * Assumption: all entities are unique for comparing lists
     *             all items are unique for comparing lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonEqual(DungeonResponse res1, DungeonResponse res2) {
        if (!res1.getDungeonName().equals(res2.getDungeonName())) {
            return false;
        } else if (!(res1.getBuildables().containsAll(res2.getBuildables())
            && res2.getBuildables().containsAll(res1.getBuildables()))) {
            return false;
        } else if (!res1.getGoals().equals(res2.getGoals())) {
            return false;
        } else if (!res1.getGoals().equals(res2.getGoals())) {
            return false;
        } 
        // else if (!res1.getDungeonId().equals(res2.getDungeonId())) {
        //     return false;
        // }
        
        // compare inventory list ignoring order and ids
        ArrayList<ItemResponse> inv1 = new ArrayList<ItemResponse>(res1.getInventory());
        ArrayList<ItemResponse> inv2 = new ArrayList<ItemResponse>(res2.getInventory());
        Collections.sort(inv1, new ItemResponseComparator());
        Collections.sort(inv2, new ItemResponseComparator());
        if (inv1.size() != inv2.size()) {
            return false;
        } 
        int inventoryLen = inv1.size();
        for (int i = 0; i < inventoryLen; i++) {
            if (!ResponseHelp.itemEqual(inv1.get(i), inv2.get(i))) {
                return false;
            }
        }

        // compare entities list ignoring order, ids and layer
        ArrayList<EntityResponse> ents1 = new ArrayList<EntityResponse>(res1.getEntities());
        ArrayList<EntityResponse> ents2 = new ArrayList<EntityResponse>(res2.getEntities());
        Collections.sort(ents1, new EntityResponseComparator());
        Collections.sort(ents2, new EntityResponseComparator());
        
        int entitiesLen = ents1.size();
        for (int i = 0; i < entitiesLen; i++) {
            if (!ResponseHelp.entityEqual(ents1.get(i), ents1.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compare two DungeonResponse ignoring animations, id and ordering of lists
     * Assumption: all entities are unique for comparing lists
     *             all items are unique for comparing lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonEqualWithLayer(DungeonResponse res1, DungeonResponse res2) {
        // compare entities list ignoring order and ids
        ArrayList<EntityResponse> ents1 = new ArrayList<EntityResponse>(res1.getEntities());
        ArrayList<EntityResponse> ents2 = new ArrayList<EntityResponse>(res2.getEntities());
        Collections.sort(ents1, new EntityResponseComparator());
        Collections.sort(ents2, new EntityResponseComparator());
        
        int entitiesLen = ents1.size();
        for (int i = 0; i < entitiesLen; i++) {
            if (!ResponseHelp.entityEqualWithLayer(ents1.get(i), ents1.get(i))) {
                return false;
            }
        }

        return dungeonEqual(res1, res2);
    }

    /**
     * Compare two DungeonResponse ignoring animations, id and ordering of lists
     * Assumption: all entities are unique for comparing lists
     *             all items are unique for comparing lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonEqualWithId(DungeonResponse res1, DungeonResponse res2) {
        if (!res1.getDungeonId().equals(res2.getDungeonId())) {
            return false;
        }

        // inventory list ignoring order
        ArrayList<ItemResponse> inv1 = new ArrayList<ItemResponse>(res1.getInventory());
        ArrayList<ItemResponse> inv2 = new ArrayList<ItemResponse>(res2.getInventory());
        Collections.sort(inv1, new ItemResponseComparator());
        Collections.sort(inv2, new ItemResponseComparator());
        if (inv1.size() != inv2.size()) {
            return false;
        }
        int inventoryLen = inv1.size();
        for (int i = 0; i < inventoryLen; i++) {
            if (!ResponseHelp.itemEqualWithId(inv1.get(i), inv2.get(i))) {
                return false;
            }
        }

        // compare entities list ignoring order and layers
        ArrayList<EntityResponse> ents1 = new ArrayList<EntityResponse>(res1.getEntities());
        ArrayList<EntityResponse> ents2 = new ArrayList<EntityResponse>(res2.getEntities());
        Collections.sort(ents1, new EntityResponseComparator());
        Collections.sort(ents2, new EntityResponseComparator());
        
        int entitiesLen = ents1.size();
        for (int i = 0; i < entitiesLen; i++) {
            if (!ResponseHelp.entityEqualWithId(ents1.get(i), ents1.get(i))) {
                return false;
            }
        }

        return dungeonEqual(res1, res2);
    }
    

    /**
     * Compare two DungeonResponse ignoring animations and ordering of lists
     * Assumption: all entities are unique for comparing lists
     *             all items are unique for comparing lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonEqualWithLayerAndId(DungeonResponse res1, DungeonResponse res2) {
        // compare entities list ignoring order and ids
        ArrayList<EntityResponse> ents1 = new ArrayList<EntityResponse>(res1.getEntities());
        ArrayList<EntityResponse> ents2 = new ArrayList<EntityResponse>(res2.getEntities());
        Collections.sort(ents1, new EntityResponseComparator());
        Collections.sort(ents2, new EntityResponseComparator());
        
        int entitiesLen = ents1.size();
        for (int i = 0; i < entitiesLen; i++) {
            if (!ResponseHelp.entityEqualWithLayerAndId(ents1.get(i), ents1.get(i))) {
                return false;
            }
        }

        return dungeonEqualWithId(res1, res2);
    }
    
    public static boolean entityInDungeon(EntityResponse res1, DungeonResponse res2) {
    	for (EntityResponse eres : res2.getEntities()) {
    		if (entityEqual(eres, res1)) {
    			return true;
    		}
    	}
    	return false;
    }
    	
    /**
     * Get an EntityResponse of the first instance with a matching type in the
     * DungeonResponse
     * @param dgnRes the DungeonResponse that is searched
     * @param type the type that is being searched for
     * @return EntityResponse of first instance with a matching type or if not
     *         found it returns null
     */
    public static EntityResponse getEntityOfType (DungeonResponse dgnRes,
        String type) {
        List<EntityResponse> entities = dgnRes.getEntities();
        for (EntityResponse entity : entities) {
            if (entity.getType().equals(type)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Get the EntityResponse of the first Entity matching the type and position
     * of the EntityResponse entRes.
     * Note: the Entity found may have a different id and layer as those are 
     * ignored.
     * @param dgnRes the DungeonResponse that is searched
     * @param entRes the EntityResponse that is being searched for
     * @return EntityResponse of first instance with a matching type and 
     *         position as entRes or if not found it returns null
     */
    public static EntityResponse getEntity
        (DungeonResponse dgnRes, EntityResponse entRes) {
        List<EntityResponse> entities = dgnRes.getEntities();
        for (EntityResponse entity : entities) {
            if (ResponseHelp.entityEqual(entity, entRes)) {
                return entity;
            }
        }
        return null;
    }
}
