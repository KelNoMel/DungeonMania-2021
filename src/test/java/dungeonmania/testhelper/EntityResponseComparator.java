package dungeonmania.testhelper;

import java.util.Comparator;

import dungeonmania.response.models.EntityResponse;

/**
 * Compare two EntitiyResponse to be greater than, less than, or equal to
 * @param res1
 * @param res2
 * @return res1 > res2: +, res1 = res2: 0, res1 < res2: -
 */
public class EntityResponseComparator implements Comparator<EntityResponse>{
    @Override
    public int compare(EntityResponse res1, EntityResponse res2) {
        if (res1.isInteractable() != res2.isInteractable()) {
            return res1.isInteractable() ? 1 : -1;
        } else if (!res1.getType().equals(res2.getType())) {
            return res1.getType().compareTo(res2.getType());
        } else if (res1.getPosition().getX() != res2.getPosition().getX()) {
            return res1.getPosition().getX() > res2.getPosition().getX() ? 1 : -1;
        } else if (res1.getPosition().getY() != res2.getPosition().getY()) {
            return res1.getPosition().getY() > res2.getPosition().getY() ? 1 : -1;
        } else if (!res1.getId().equals(res2.getId())) {
            return res1.getId().compareTo(res2.getId());
        }
        return 0;
    }
}
