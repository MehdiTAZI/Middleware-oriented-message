package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.Comparator;

public class PriorityEventModelComparator implements Comparator<EventModel>, Serializable {

    /**
     * Compare two events.
     * 
     * First, it try to compare by their priority. The higher priority, the
     * first. If equals , compare by their creation timestamp. The older
     * (lowest), the first
     * 
     * @param Event
     *            1 and Event 2 Events to compare.
     */
    @Override
    public synchronized int compare(EventModel evt1, EventModel evt2) {

	if (evt1.getPriority() > evt2.getPriority()) {
	    return -1;
	}
	if (evt1.getPriority() < evt2.getPriority()) {
	    return 1;
	}
	if (evt1.getCreationdate() < evt2.getCreationdate()) {
	    return -1;
	}
	if (evt1.getCreationdate() >= evt2.getCreationdate()) {
	    return 1;
	}
	return 0;
    }

}
