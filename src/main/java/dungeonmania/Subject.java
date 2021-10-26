package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
	
	final List<Observer> listObservers = new ArrayList<Observer>();
        
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
