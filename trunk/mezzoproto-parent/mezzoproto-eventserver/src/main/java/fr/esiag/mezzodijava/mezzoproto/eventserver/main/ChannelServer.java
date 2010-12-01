package fr.esiag.mezzodijava.mezzoproto.eventserver.main;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.eventserver.impl.ChannelImpl;

public class ChannelServer {
		
	public static void main(String[] args) {
		
		try {					
			
		ORB orb=ORB.init(args, null);
		POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
		String channelName1="MEZZO-DI-JAVA-1";
		ChannelImpl channel=new ChannelImpl(channelName1, 20);				
		nc.rebind(nc.to_name(channelName1), poa.servant_to_reference(channel));
		
		String channelName2="MEZZO-DI-JAVA-2";
		ChannelImpl channel2=new ChannelImpl(channelName2, 20);				
		nc.rebind(nc.to_name(channelName2), poa.servant_to_reference(channel2));
	
		System.out.println("channel "+channelName1+" is running...");
		System.out.println("channel "+channelName2+" is running...");
		orb.run();
		} catch (Exception e) {
			throw new RuntimeException(e);
			//System.out.println("erreur : "+e.toString());
		}
	}

}
