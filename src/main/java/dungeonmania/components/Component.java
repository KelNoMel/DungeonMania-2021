package dungeonmania.components;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.InputState;
import dungeonmania.Observer;
import dungeonmania.Subject;
import dungeonmania.entities.Entity;

public abstract class Component implements Subject {
    
	private Entity owningEntity;
    protected int updateOrder;
    private List<Observer> observingComponents = new ArrayList<Observer>();

    public Component(Entity owningEntity, int updateOrder) {
        this.owningEntity = owningEntity;
        this.updateOrder = updateOrder;
        
        owningEntity.addComponent(this);
    }

    /**
     * getter of owning entity
     * @return owning entity
     */
    public Entity getEntity() {
        return owningEntity;
    }
    
	////////////////////////////////////////////////////////////////////////////////
	///                               Run Component                              ///
	////////////////////////////////////////////////////////////////////////////////
    
    public abstract void processInput(InputState inputState);
    
    public abstract void updateComponent();
    
    public int getUpdatedOrder() {
        return updateOrder;
    }

	////////////////////////////////////////////////////////////////////////////////
	///                                 Observer?                                ///
	////////////////////////////////////////////////////////////////////////////////
        
    @Override
    public void attach(Observer c) {
    	if(!observingComponents.contains(c)) {
    		observingComponents.add(c);
    	}
    }

    @Override
    public void detach(Observer c) {
        observingComponents.remove(c);
    }

    @Override
    public void notifyObservers() {
    	for(Observer c : observingComponents) {
    		c.updateObserver(this);
    	}
    }

	
}
