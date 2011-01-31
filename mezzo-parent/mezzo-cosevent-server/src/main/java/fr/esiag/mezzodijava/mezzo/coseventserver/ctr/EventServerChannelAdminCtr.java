package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;
import java.nio.channels.Channel;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class EventServerChannelAdminCtr
{
	private String topic;
	private Channel channel;
	private ORB orb;

 
	public EventServerChannelAdminCtr(String topic) 
	{
		this.topic = topic;
		this.orb = BFFactory.createOrb(null, null);
		this.channel = BFFactory.createChannelCtr(topic);
	}
	
	// a verifier -----------;
	public Channel createChannel(String topic,int capacity)
	{
		this.topic=topic;
		this.orb = BFFactory.createOrb(null, null);
		this.channel=(Channel) BFFactory.createChannel(topic, capacity)
		 
		return  channel;		
	}


}
