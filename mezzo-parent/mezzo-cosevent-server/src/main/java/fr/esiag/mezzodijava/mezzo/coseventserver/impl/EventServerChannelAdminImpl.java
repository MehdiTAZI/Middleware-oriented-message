package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;


import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe EventServerChannelAdminImpl
 *
 * implementation of the EventServerChannelAdmin IDL
 * Interface
 *
 * UC n°07: US76,US160,US166 
 *
 * @author Mezzo-Team
 *
 */
public class EventServerChannelAdminImpl implements EventServerChannelAdminOperations 
{
	private EventServerChannelAdminCtr eventServerChannelAdminctrl;
   
    private String eventServerName;
    
    
    
	public EventServerChannelAdminImpl(String eventServerName)
	{
		this.eventServerName = eventServerName;		
		this.eventServerChannelAdminctrl = BFFactory.createEventServerChannelAdminCtr(eventServerName);
	}
	
	

	@Override
	public long createChannel(String topic, int capacity)
			throws ChannelAlreadyExistsException {	
		return eventServerChannelAdminctrl.createChannel(topic, capacity);
	}

	@Override
	public ChannelAdmin getChannel(long uniqueServerChannelId)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyChannel(long uniqueServerChannelId)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		
	}

	@Override
	public void changeChannelCapacity(long uniqueServerChannelId, int capacity)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException,
			fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException {
	}

}
