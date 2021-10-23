package dungeonmania;

import java.util.ArrayList;

public abstract class Component implements Subject {
    
	private Entity owningEntity;
    protected int updateOrder;
    private ArrayList<Observer> observingComponents = new ArrayList<Observer>();

    public Component(Entity OwningEntity, int updateOrder) {
        this.owningEntity = OwningEntity;
        this.updateOrder = updateOrder;
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
    
    public abstract void update();
    
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
    		c.update(this);
    	}
    }
}
