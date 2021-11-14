package dungeonmania.components;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.redstone.ActivationLogic;

public class RedstoneComponent extends Component {

	private List<RedstoneComponent> adjRedstone = new ArrayList<>();
	private int powerLevel;
	private ActivationLogic activationLogic;
	
	public RedstoneComponent(Entity owningEntity, int updateOrder) {
		super(owningEntity, updateOrder);
		
		List<Entity> cardinallyAdjacent = owningEntity.getDungeon().getEntitiesInRadius(owningEntity.getPosition(), 1.0);
		cardinallyAdjacent.remove(owningEntity);
		for (Entity adj : cardinallyAdjacent) {
			RedstoneComponent adjRedstone = adj.getComponent(RedstoneComponent.class);
			if (adjRedstone != null) {
				// On construction adds all neighbouring redstone
				addAdjacent(adjRedstone);
				// Add myself to adjacent redstone
				adjRedstone.addAdjacent(this);
			}
		}
	}
	
	private void addAdjacent(RedstoneComponent adj) {
		if (!adjRedstone.contains(adj)) adjRedstone.add(adj);
	}
	
	public void processInput(InputState inputState) {}
	public void updateComponent() {}

	public void loadJSONComponentSpecific(JSONObject entityData) {
		if (entityData.has("power")) {
			this.powerLevel = entityData.getInt("power");
		}
		
		if (entityData.has("logic")) {
			activationLogic = ActivationLogic.createLogic(entityData.getString("logic"));
		}
	}
	public void saveJSONComponentSpecific(JSONObject entityJSON) {
		entityJSON.put("power", powerLevel);
		if (activationLogic != null) {
			entityJSON.put("logic", activationLogic.getLogic());
		}
	}
	
	public void powerOn() {
		increasePower(16, this);
	}
	
	public void powerOff() {
		List<RedstoneComponent> repropagaters = new ArrayList<>();
		decreasePower(0, this, repropagaters);
		for (RedstoneComponent c : repropagaters) {
			c.increasePower(c.getPower(), c);
		}
	}
	
	private void increasePower(int newPower, RedstoneComponent settingComponent) {
		newPower = calcPowerLevel(newPower);
		
		powerLevel = newPower;
		
		// Update all adjacent redstone
		for (RedstoneComponent adj : adjRedstone) {
			if (adj == settingComponent) continue;
			if (powerLevel-1 > adj.getPower()) {
				adj.increasePower(powerLevel-1, this);
			}
		}
	}
	
	private void decreasePower(int newPower, RedstoneComponent settingComponent, List<RedstoneComponent> repropagaters) {
		newPower = calcPowerLevel(newPower);
		
		int previousPower = powerLevel;
		powerLevel = newPower;
		
		// Update all adjacent redstone
		for (RedstoneComponent adj : adjRedstone) {
			if (adj == settingComponent) continue;
			// Only decrease the power of elements powered by this component
			if (previousPower > adj.getPower()) {
				repropagaters.remove(adj);
				adj.decreasePower(newPower-1, this, repropagaters);
			} else {
				if (!repropagaters.contains(adj)) repropagaters.add(adj);
			}
		}
	}
	
	private int calcPowerLevel(int attemptLevel) {
		if (attemptLevel <= 0) return 0;
		if (attemptLevel >= 16) return 16;
		return attemptLevel;
	}
	
	public int getPower() {
		return powerLevel;
	}
	
	public boolean isActivated() {
		
		if (powerLevel > 0) return true;
		
		return false;
	}
		/*boolean logicFulfilled = false;
		switch (activationLogic) {
			case AND:
				if (numAdjActivated() >= 2) logicFulfilled = true;
				break;
			case OR:
				if (numAdjActivated() >= 1) logicFulfilled = true;
				break;
			case XOR:
				if (numAdjActivated() == 1) logicFulfilled = true;
				break;
			case NOT:
				if (numAdjActivated() == 0) logicFulfilled = true;
				break;
			case COAND:
				if (numAdjActivated() >= 2) {
					//TODO
					logicFulfilled = true;
				}
				break;
			default:
				logicFulfilled = true;
		}
		
		if (logicFulfilled && powerLevel > 0) {
			return true;
		}
		return false;
	}
	
	private int numAdjActivated() {
		int numActivated = 0;
		for (RedstoneComponent adjComp : adjRedstone) {
			if (adjComp.isActivated()) {
				numActivated++;
			}
		}
		return numActivated;
	}*/
}
