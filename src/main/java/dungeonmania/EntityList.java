package dungeonmania;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;

@SuppressWarnings("serial")
public class EntityList extends ArrayList<Entity> {
	private boolean updatingActors = false;
	private ArrayList<Entity> newEntities = new ArrayList<>();
	private ArrayList<Entity> deadEntities = new ArrayList<>();
	
	public EntityList() {
		super();
	}
	
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
		
    	removeDeadEntities();
    	
    	for (Entity newEntity : newEntities) {
    		add(newEntity);
    	}
    	newEntities.clear();
	}
	
	public void removeDeadEntities() {
		removeAll(deadEntities);
		deadEntities.clear();
	}
	
	@Override
	public boolean add(Entity e) {
		if (updatingActors) {
			newEntities.add(e);
		} else {
			super.add(e);
		}
		Comparable<Integer> cmp = (Comparable<Integer>) e.getUpdateOrder();
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1).getUpdateOrder()) < 0; i--) {
            Collections.swap(this, i, i-1);
        }
		return true;
	}
	
	public boolean queueAdd(Entity e) {
		newEntities.add(e);
		return true;
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
	
	public JSONArray toJSON() {
		JSONArray entities = new JSONArray();
		for (Entity e : this) {
			entities.put(e.toJSON());
		}
		return entities;
	}
	
	// Returns a list of enitities by a certain type
	// Not used now
	public List<Entity> getEntitiesByType(Class<?> classType) {
		List<Entity> entTypeList = new ArrayList<>();
		for (Entity e : this) {
			if (classType.isInstance(e)) {
				entTypeList.add(e);
			}
		}
		return entTypeList;
	}
	
	public int numEntitiesOfType(Class<?> classType) {
    	return getEntitiesByType(classType).size();
    }
}
