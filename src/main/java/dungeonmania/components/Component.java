package dungeonmania.components;

import org.json.JSONObject;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public abstract class Component {
    
	private Entity owningEntity;
    protected int updateOrder;

    public Component(Entity owningEntity, int updateOrder) {
        this.owningEntity = owningEntity;
        this.updateOrder = updateOrder;
        
        owningEntity.addComponent(this);
    }

    public Entity getEntity() {
        return owningEntity;
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                               Run Component                              ///
	////////////////////////////////////////////////////////////////////////////////
    
    public abstract void processInput(InputState inputState);
    
    public abstract void updateComponent();
    
    public int getUpdateOrder() {
        return updateOrder;
    }

	public abstract void loadJSONComponentSpecific(JSONObject entityData);
	public abstract void addJSONComponentSpecific(JSONObject entityJSON);

}
