package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.publisher.ChannelPublisher;

public class EventServerChannelAdminCtr
{
	private String eventServerName;
	private Properties props = new Properties();
	private ORB orb;


	public EventServerChannelAdminCtr(String eventServerName) 
	{
		this.eventServerName = eventServerName;
		String []args=new String[]{"-ORBInitRef NameService=corbaloc::127.0.0.1:1050/NameService",
				"-Djacorb.home=C:\\mezzodev\\jacorb-2.3.1",
				"-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB",
				"-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
		};
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("eventserver.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.orb = BFFactory.createOrb(args, props);		
	}


	public long createChannel(String topic,int capacity) throws ChannelAlreadyExistsException
	{
		this.eventServerName=topic;
		return  BFFactory.createChannel(topic, capacity);		
	}
	
	public ChannelAdmin getChannel(long uniqueServerChannelId)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		return BFFactory.getChannelAdmin(uniqueServerChannelId);
	}
}