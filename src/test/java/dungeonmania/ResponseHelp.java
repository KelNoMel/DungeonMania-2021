package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;


public class ResponseHelp {
    public static boolean entityCompareWithoutIdOrLayer(
        EntityResponse res1, EntityResponse res2) {
        if (res1.isInteractable() != res2.isInteractable()) {
            return false;
        } else if (!res1.getType().equals(res2.getType())) {
            return false;
        } else if (!res1.getPosition().equals(res2.getPosition())) {
            return false;
        }
        return true;
    }
    
    public static boolean entityCompareWithoutId(
        EntityResponse res1, EntityResponse res2) {
        if (res1.isInteractable() != res2.isInteractable()) {
            return false;
        } else if (!res1.getType().equals(res2.getType())) {
            return false;
        } else if (!res1.getPosition().equals(res2.getPosition())) {
            return false;
        }
        return true;
    }

    public static boolean entityCompare(
        EntityResponse res1, EntityResponse res2) {
        
        return true;
    }

    public static boolean itemCompareWithoutId(
        ItemResponse res1, ItemResponse res2) {
        if (!res1.getType().equals(res2.getType())) {
            return false;
        }
        return true;
    }

    public static boolean itemCompare(
        ItemResponse res1, ItemResponse res2) {
        if (!res1.getType().equals(res2.getType())) {
            return false;
        } else if (res1.getId().equals(res2.getId())) {
            return false;
        }
        return true;
    }
    
    /**
     * Compare two DungeonResponse ignoring animations, id and ordering of lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonCompareWithoutId(DungeonResponse res1, DungeonResponse res2) {
        if (!res1.getDungeonName().equals(res2.getDungeonName())) {
            return false;
        } else if (res1.get) {
            return false;
        } else if () {
            return false;
        }

        return true;
    }

    /**
     * Compare two DungeonResponse ignoring animations and ordering of lists
     * @param res1
     * @param res2
     * @return true if responses are equivalent, false otherwise
     */
    public static boolean dungeonCompare(DungeonResponse res1, DungeonResponse res2) {
        if (!res1.getDungeonName().equals(res2.getDungeonName())) {
            return false;
        } else if (res1.get) {
            return false;
        } else if () {
            return false;
        }

        return true;
    }
}
