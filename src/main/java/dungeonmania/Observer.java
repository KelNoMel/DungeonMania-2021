package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public interface Observer {
	
	final List<Subject> attachedList = new ArrayList<>();

	public abstract void updateObserver(Subject sub);

	default public void addToAttachedList(Subject sub) {
        attachedList.add(sub);
    }

    default public void detachAllSubjects() {
        for (Subject sub : attachedList) {
            sub.detach(this);
        }
    }
	
}

