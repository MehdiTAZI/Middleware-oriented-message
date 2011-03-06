package fr.esiag.mezzodijava.mezzo.costimeserver.impl;


import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.TimeServiceOperations;
import fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;

/**
 * TimeServiceImpl : implementation of the TimeService IDL Interface
 * 
 * UC n°06: US18 (+ children).
 * 
 * @author Mezzo-Team
 * 
 */

public class TimeServiceImpl implements TimeServiceOperations{

	private TimeServiceCtr ctr;
	
	/**
	 * Constructor
	 *
	 * @param ctr
	 *            the TimeServiceCtr
	 */
	public TimeServiceImpl(TimeServiceCtr ctr){
		this.ctr=ctr;
	}
	
	/**
	 * Get the controller
	 *
	 * @return TimeServiceCtr
	 *
	 */
	public TimeServiceCtr getCtr() {
		return ctr;
	}
	
	/**
     * Subscribe a component to the channel.
     * 
     * The server will push the time to this component.
     * 
     * @param component
     * 			a component of type Synchronizable
     * @throws AlreadyRegisteredException
     *             If already present in the list.
     * @see fr.esiag.mezzodijava.mezzo.costime.TimeServiceOperations#subscribe(Synchronizable cc)
     */
	@Override
	public void subscribe(Synchronizable cc) throws AlreadyRegisteredException{
		ctr.subscribe(cc);
	}
	
	/**
     * Unsubscribe a component from the channel.
     * 
     * remove the component from the list 
     * 
     * @param component
     * 			a component of type Synchronizable
     * @throws NotRegisteredException
     *             If the component is not registered.
     * @see fr.esiag.mezzodijava.mezzo.costime.TimeServiceOperations#unsubscribe(Synchronizable cc)
     */
	@Override
	public void unsubscribe(Synchronizable cc) throws NotRegisteredException {
		ctr.unsubscribe(cc);
	}
}
