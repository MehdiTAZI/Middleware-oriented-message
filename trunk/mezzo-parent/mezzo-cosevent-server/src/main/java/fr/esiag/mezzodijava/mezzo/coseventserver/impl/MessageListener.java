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

	/**
	 * push method
	 * @param evt the event that we gonna push
	 * @throws ConsumerNotFoundException if consumer is disconnected
	 */
    public void receive(Event evt) throws ConsumerNotFoundException;

}
