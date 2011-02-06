package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class EventServerChannelAdminCtr
{
	private String eventServerName;
	private Properties props = new Properties();
	private ORB orb;


	public ORB getOrb() {
		return orb;
	}
	public void setOrb(ORB orb) {
		this.orb = orb;
	}
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
	public EventServerChannelAdminCtr(){
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
	public void destroyChannel(long uniqueServerChannelId)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		try {
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));
			try {
				if(BFFactory.getChannel(uniqueServerChannelId)!=null){
					nc.unbind(nc.to_name(BFFactory.getChannel(uniqueServerChannelId).getTopic()));
					BFFactory.destroy(uniqueServerChannelId);
				}
				else throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();
			} catch (NotFound e) {
				e.printStackTrace();
			} catch (CannotProceed e) {
				e.printStackTrace();
			} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
				e.printStackTrace();
			}
		} catch (InvalidName e) {
			e.printStackTrace();
		}

	}
	public void changeChannelCapacity(long uniqueServerChannelId, int capacity)
	throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException,
	fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException {
		if(BFFactory.getChannel(uniqueServerChannelId)==null)
			throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();
		if(BFFactory.getChannel(uniqueServerChannelId).getCapacity()>capacity)
			throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException();
		BFFactory.changeChannelCapacity(BFFactory.getChannel(uniqueServerChannelId), capacity);
		
	}
}