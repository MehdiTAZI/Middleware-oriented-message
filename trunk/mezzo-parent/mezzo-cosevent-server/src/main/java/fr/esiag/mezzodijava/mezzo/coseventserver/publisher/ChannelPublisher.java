package fr.esiag.mezzodijava.mezzo.coseventserver.publisher;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ThreadEvent;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;

public class ChannelPublisher {

	public static void publish(ChannelAdminImpl channelAdminImpl,ORB orb){

		ThreadEvent th = new ThreadEvent(channelAdminImpl.getTopic());
		Thread thread = new Thread(th);
		thread.start();
		try{
			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			nc.rebind(nc.to_name(channelAdminImpl.getTopic()), poa.servant_to_reference(new ChannelAdminPOATie(channelAdminImpl)));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}