package dungeonmania;

import java.util.ArrayList;
import java.util.LinkedList;

public interface Subject {
	
	final LinkedList<Observer> listObservers = new LinkedList<Observer>();
        
    default void attach(Observer o) {
    	if (!listObservers.contains(o)) {
    		listObservers.add(o);
    	}
    }
    
	default void detach(Observer o) {
		listObservers.remove(o);
	}
	
	default void notifyObservers() {
		for (Observer o : listObservers) {
			o.updateObserver(this);
		}
	}

}
