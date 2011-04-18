package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
/**
 * 
 * @author MEZZODIJAVA
 *
 */
public class PriorityEventComparator implements Comparator<Event> {

	private static Logger log = LoggerFactory.getLogger(PriorityEventComparator.class);

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

    	log.debug("Compare {} with {}",evt1.header.code,evt2.header.code);
	if (evt1.header.priority > evt2.header.priority) {
	    return -1;
	}
	if (evt1.header.priority < evt2.header.priority) {
	    return 1;
	}
	if (evt1.header.creationdate < evt2.header.creationdate) {
	    return -1;
	}
	if (evt1.header.creationdate >= evt2.header.creationdate) {
	    return 1;
	}
	return 0;
    }

}
