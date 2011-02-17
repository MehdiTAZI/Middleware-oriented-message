package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Comparator;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class PriorityEventComparator implements Comparator<Event> {

	@Override
	public synchronized int compare(Event evt1, Event evt2) {
		
		if (evt1.header.priority < evt2.header.priority)
        {
            return -1;
        }
        if (evt1.header.priority > evt2.header.priority)
        {
            return 1;
        }
        return 0;

	}

}
