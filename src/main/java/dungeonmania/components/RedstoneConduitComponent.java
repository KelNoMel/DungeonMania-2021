package dungeonmania.components;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.redstone.LogicType;
import dungeonmania.entities.redstone.RedstoneConduit;
import dungeonmania.entities.redstone.RedstoneSource;

public class RedstoneConduitComponent extends Component implements RedstoneConduit  {

	private LogicType logic;
	
	public RedstoneConduitComponent(Entity owningEntity, int updateOrder) {
		super(owningEntity, updateOrder);
		
		List<Entity> cardinallyAdjacent = owningEntity.getDungeon().getEntitiesInRadius(owningEntity.getPosition(), 1);
		for (Entity adj : cardinallyAdjacent) {
			RedstoneSourceComponent src = adj.getComponent(RedstoneSourceComponent.class);
			if (src != null) {
				// On constuction adds all neighbouring conduits as observers
				src.registerObserver(this);
			}
		}
	}

	public void processInput(InputState inputState) {}
	public void updateComponent() {}

	public void loadJSONComponentSpecific(JSONObject entityData) {
		if (entityData.has("logic")) {
			logic = LogicType.getLogicType(entityData.getString("logic"));
		}
	}
	
	public void addJSONComponentSpecific(JSONObject entityJSON) {
		entityJSON.put("logic", logic.getType());
	}

	public void updateConduit(RedstoneSource src) {
		
	}
}
