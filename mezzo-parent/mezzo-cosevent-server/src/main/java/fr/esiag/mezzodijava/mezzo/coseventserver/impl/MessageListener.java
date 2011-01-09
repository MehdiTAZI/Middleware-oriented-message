package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public interface MessageListener {
	
	public void receive(Event evt) throws ConsumerNotFoundException;
	
}
