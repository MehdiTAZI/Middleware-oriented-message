package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;;
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
    private String topic;
    
    
    
	public long EventServerChannelAdminImpl(String topic,long capacity)
	{
		this.topic = topic;		
		this.eventServerChannelAdminctrl = BFFactory.createEventServerChannelAdminCtr(topic);
	}
	
	public void changeChannelCapacity(long uniqueServerChannelId,long capacity)throws ChannelNotFoundException,CannotReduceCapacityException
	{	
	}

}
