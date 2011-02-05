package fr.esiag.mezzodijava.mezzo.coseventserver.main;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;

public class Test {

	public Test(String args[]) {
		try{
			Properties props = new Properties();
			try {
			    props.load(this.getClass().getClassLoader()
				    .getResourceAsStream("eventserver.properties"));
			} catch (IOException e) {
			    // TODO log here
			    
			}
			ORB orb=ORB.init(args,props);
			POA rootPOA=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			Object objRef= orb.resolve_initial_references("NameService");
			NamingContextExt ncRef =NamingContextExtHelper.narrow(objRef);
			EventServerChannelAdmin channelAdmin=EventServerChannelAdminHelper.narrow(ncRef.resolve_str("MEZZO-SERVER"));
			long id=channelAdmin.createChannel("MEZZO", 30);
			Thread.sleep(10000);
			channelAdmin.destroyChannel(id);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test(args);
	}

}
