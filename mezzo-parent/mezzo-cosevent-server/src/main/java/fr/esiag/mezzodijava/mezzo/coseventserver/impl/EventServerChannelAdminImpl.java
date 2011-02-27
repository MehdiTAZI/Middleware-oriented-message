package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe EventServerChannelAdminImpl
 *
 * implementation of the EventServerChannelAdmin IDL
 * Interface
 *
 * UC n°07: US10 (+US children)
 *
 * @author Mezzo-Team
 *
 */
public class EventServerChannelAdminImpl implements EventServerChannelAdminOperations 
{
	private EventServerChannelAdminCtr eventServerChannelAdminctrl;

	private String eventServerName;


	/**
     * Constructor for an EventServerChannelAdmin implementation. 
     * Build the underlying EventServerChannelAdminCtr.
     *
     * @param eventServerName
     *            EventServer Name
     */
	public EventServerChannelAdminImpl(String eventServerName)
	{
		this.eventServerName = eventServerName;		
		this.eventServerChannelAdminctrl = BFFactory.createEventServerChannelAdminCtr(eventServerName);
	}


	/**
     * Create a channel with a topic and an initial capacity
     *
     * @param topic 
     * 			Channel Topic
     * @param capacity 
     * 			Channel maximum capacity
     * @return an UID for channel identification
     * @see fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations#createChannel(String topic, int capacity)
     *
     */
	@Override
	public long createChannel(String topic, int capacity)
	throws ChannelAlreadyExistsException {	
		return eventServerChannelAdminctrl.createChannel(topic, capacity);
	}

	/**
	 * Find the Channel identified with an uid
	 *
	 * @param uniqueServerChannelId
	 *            The uid of the channel to find  
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist   
	 * @return the Channel's Administrator
	 * @see fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations#getChannel(long uniqueServerChannelId)
	 * 
	 */
	@Override
	public ChannelAdmin getChannel(long uniqueServerChannelId)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		return eventServerChannelAdminctrl.getChannel(uniqueServerChannelId);
	}

	/**
	 * Destroy the Channel identified with an uid; delete the channel and
	 * all the list of events and proxies
	 *
	 * @param uniqueServerChannelId
	 *            The uid of the channel to destroy
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist 
	 * @see fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations#destroyChannel(long uniqueServerChannelId)
	 */
	@Override
	public void destroyChannel(long uniqueServerChannelId)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		eventServerChannelAdminctrl.destroyChannel(uniqueServerChannelId);
	}

	/**
	 * Change the maximum number of composants connected at the same time
	 *
	 * @param uniqueServerChannelId
	 *            The uid of the channel to find     
	 * @param capacity
	 * 			  The new capacity to apply
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist 
	 * @throws CannotReduceCapacityException
	 *             If the channel is full
	 * @see fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations#changeChannelCapacity(long uniqueServerChannelId, int capacity)
	 */
	@Override
	public void changeChannelCapacity(long uniqueServerChannelId, int capacity)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException,
	fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException {
		eventServerChannelAdminctrl.changeChannelCapacity(uniqueServerChannelId, capacity);
	}

}
