package dungeonmania;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

@SuppressWarnings("serial")
public class EntityList extends ArrayList<Entity> {
	private boolean updatingActors = false;
	private ArrayList<Entity> newEntities = new ArrayList<>();
	private ArrayList<Entity> deadEntities = new ArrayList<>();
	
	public void processInput(InputState inputState) {
		updatingActors = true;
    	for (Entity e : this) {
    		e.processInput(inputState);
    	}
		
    	updatingActors = false;
	}
	
	public void updateEntities() {
    	updatingActors = true;
		for (Entity e : this) {
    		e.update();
    	}
		updatingActors = false;
		
    	for (Entity e : this) {
    		if (e.getState() == EntityState.DEAD) {
    			deadEntities.add(e);
    		}
    	}
		
    	removeAll(deadEntities);    	
    	deadEntities.clear();
    	
    	addAll(newEntities);
    	newEntities.clear();
	}
	
	@Override
	public boolean add(Entity e) {
		if (updatingActors) {
			newEntities.add(e);
			return true;
		} else {
			return super.add(e);
		}
	}
	
	public boolean isUpdating() {
		return updatingActors;
	}
	
	public void transferEntity(EntityList dest, Entity transferEntity) throws InvalidParameterException {
		if (!contains(transferEntity)) {
			throw new InvalidParameterException("This array does not contain this entity");
		}
		dest.add(transferEntity);
		deadEntities.add(transferEntity);
	}
}
