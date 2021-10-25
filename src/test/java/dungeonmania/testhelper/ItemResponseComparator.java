package dungeonmania.testhelper;

import java.util.Comparator;

import dungeonmania.response.models.ItemResponse;

/**
 * Compare two ItemResponse to be greater than, less than, or equal to
 * @param res1
 * @param res2
 * @return res1 > res2: +, res1 = res2: 0, res1 < res2: -
 */
public class ItemResponseComparator implements Comparator<ItemResponse>{
    @Override
    public int compare(ItemResponse res1, ItemResponse res2) {
        if (!res1.getType().equals(res2.getType())) {
            return res1.getType().compareTo(res2.getType());
        } else if (!res1.getId().equals(res2.getId())) {
            return res1.getId().compareTo(res2.getId());
        }
        return 0;
    }
}
