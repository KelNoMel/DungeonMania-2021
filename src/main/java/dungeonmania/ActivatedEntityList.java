package dungeonmania;

import dungeonmania.entities.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ActivatedEntityList extends ArrayList<Entity> {
    private ArrayList<Entity> activatedList = new ArrayList<>();
    private List<String> approvedTypeList = new ArrayList<>();
    private Dungeon owningDungeon;

    public ActivatedEntityList(Dungeon owningDungeon) {
        super();
        // Entities which can be activated, might be incomplete
        approvedTypeList = Arrays.asList("bomb", "switch", "wire", "lightbulb");
        this.owningDungeon = owningDungeon;
    }

    // Should be used when a new source is added/activated (Basically activated lightswitches I think)
    // Because it's recursive, you don't need to manually add every adjacent wire/bomb
    // since they just get added automatically
    @Override
    public boolean add(Entity e) {
        if (approvedTypeList.contains(e.getType())) {
            activatedList.add(e);
            // Chain activate adjacent activatable objects
            // Useful for wires/bombs etc.
            for (Entity adj : owningDungeon.getEntitiesInRadius(e.getPosition(), 1)) {
                add(adj);
            }
            return true;
        }
        return false;
    }

    // This removes sources of activation (switches go off)
    // This doesn't automatically disconnect dependent adjacent entities
    // Disconnecting dependent entities will be handled by an ActivationComponent (TODO)
    public boolean remove(Entity e) {
        if (approvedTypeList.contains(e.getType())) {
            activatedList.remove(e);
            return true;
        }
        return false;
    }

    public boolean contains(Entity e) {
        return (activatedList.contains(e));
    }
}
