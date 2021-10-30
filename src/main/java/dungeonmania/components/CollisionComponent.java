package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public class CollisionComponent extends Component {

//	public List<Entity> collision
	
	public CollisionComponent(Entity owningEntity, int updateOrder) {
		super(owningEntity, updateOrder);
	}

	public void processInput(InputState inputState) {}

	public void updateComponent() {}

}
