package dungeonmania.entities.statics;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Boulder can be pushed around but some entities prevent a boulder from being
 * pushed backwards.
 */
public class Boulder extends Entity {

	public Boulder(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "boulder", position, false, EntityUpdateOrder.OTHER, entitySpecificData);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {

	}
	
	/**
	 * Push a boulder in a direction if the path is not blocked
	 * @param d direction
	 * @return true if it was succeffully pushed in direction d
	 */
	public boolean moveBoulder(Direction d) {
		Position desiredMovePosition = getPosition().translateBy(d);
		
		List<Entity> moveEntities = getDungeon().getEntitiesAtPosition(desiredMovePosition);
		
		for (Entity e : moveEntities) {
			if (e instanceof Boulder || e instanceof Wall) {
				return false;
			}
		}
		
		setPosition(desiredMovePosition);
		
		return true;
	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
