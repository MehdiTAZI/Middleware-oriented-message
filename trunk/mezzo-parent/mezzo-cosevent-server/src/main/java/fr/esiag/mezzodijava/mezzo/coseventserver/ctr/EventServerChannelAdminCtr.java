package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;
import java.nio.channels.Channel;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class EventServerChannelAdminCtr
{
	private String eventServerName;
	
	private ORB orb;

 
	public EventServerChannelAdminCtr(String eventServerName) 
	{
		this.eventServerName = eventServerName;
		this.orb = BFFactory.createOrb(null, null);
		 
	}
	
	
	public long createChannel(String topic,int capacity)
	{
		this.eventServerName=topic;
		this.orb = BFFactory.createOrb(null, null);
				
		return  BFFactory.createChannel(topic, capacity);		
	}


	
}
