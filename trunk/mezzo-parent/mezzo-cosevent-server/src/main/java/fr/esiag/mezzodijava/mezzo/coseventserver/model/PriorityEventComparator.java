package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Comparator;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class PriorityEventComparator implements Comparator<Event> {

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
    public synchronized int compare(Event evt1, Event evt2) {

	if (evt1.header.priority > evt2.header.priority) {
	    return -1;
	}
	if (evt1.header.priority < evt2.header.priority) {
	    return 1;
	}
	if (evt1.header.date < evt2.header.date) {
	    return -1;
	}
	if (evt1.header.date >= evt2.header.date) {
	    return 1;
	}
	return 0;
    }

}
