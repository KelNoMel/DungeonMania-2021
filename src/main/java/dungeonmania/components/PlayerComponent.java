package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Player;

public class PlayerComponent extends Component {

	private Player player;
	public PlayerComponent(Player owningEntity, int updateOrder) {
		super(owningEntity, updateOrder);
		player = owningEntity;
	}

	public void processInput(InputState inputState) {
		player.moveComponent.setMoveDirection(inputState.getMovementDirection());
	}

	public void updateComponent() {}

	public void saveJSONComponentSpecific(JSONObject entityJSON) {}
	public void loadJSONComponentSpecific(JSONObject entityData) {}
}
