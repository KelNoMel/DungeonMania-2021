package dungeonmania.components;

import dungeonmania.InputState;
import dungeonmania.entities.Entity;

public abstract class Component {
    
	private Entity owningEntity;
    protected int updateOrder;
    private boolean expired;

    public Component(Entity owningEntity, int updateOrder) {
        this.owningEntity = owningEntity;
        this.updateOrder = updateOrder;
        this.expired = false;
        
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

    public boolean isExpired() {
        return expired;
    }

    public void setExpiry(boolean expired) {
        this.expired = expired;
    }

}
