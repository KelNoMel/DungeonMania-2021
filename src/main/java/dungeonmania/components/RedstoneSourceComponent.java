package dungeonmania.components;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.redstone.RedstoneSource;

public class RedstoneSourceComponent extends Component implements RedstoneSource {

	public RedstoneSourceComponent(Entity owningEntity, int updateOrder) {
		super(owningEntity, updateOrder);
		
		List<Entity> cardinallyAdjacent = owningEntity.getDungeon().getEntitiesInRadius(owningEntity.getPosition(), 1);
		for (Entity adj : cardinallyAdjacent) {
			RedstoneConduitComponent conduit = adj.getComponent(RedstoneConduitComponent.class);
			if (conduit != null) {
				// On constuction adds all neighbouring conduits as observers
				registerObserver(conduit);
			}
		}
	}

	public void processInput(InputState inputState) {}
	public void updateComponent() {}

	public void loadJSONComponentSpecific(JSONObject entityData) {}
	public void addJSONComponentSpecific(JSONObject entityJSON) {}
}
