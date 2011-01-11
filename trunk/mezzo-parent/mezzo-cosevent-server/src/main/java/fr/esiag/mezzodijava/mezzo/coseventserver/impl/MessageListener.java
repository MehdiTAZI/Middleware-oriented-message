package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;

/**
 * Interface MessageListener
 * 
 * To receive events, technical interface
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public interface MessageListener {
	
	public void receive(Event evt) throws ConsumerNotFoundException;
	
}
