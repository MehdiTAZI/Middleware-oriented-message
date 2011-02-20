package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminHelper;

public class CAAgentProcess {

	public CAAgentProcess(String args[]) {
		try {
			Properties props = new Properties();
			try {
				props.load(this.getClass().getClassLoader()
						.getResourceAsStream("eventclient.properties"));
			} catch (IOException e) {
				e.printStackTrace();

			}
			ORB orb = ORB.init(args, props);
			POA rootPOA = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			String nuclearChannelName = "nuclear sensor";
			System.out.println("Asking creation of Channel \""+nuclearChannelName+"\" on server \""+args[0]+"\"...");
			EventServerChannelAdmin channelAdmin = EventServerChannelAdminHelper
					.narrow(ncRef.resolve_str(args[0]));
			long id = channelAdmin.createChannel(nuclearChannelName, 3);
			System.out.println("Done.");
			
			/*String injectorSystemChannelName = "injector system state";
			System.out.println("Asking creation of Channel \""+injectorSystemChannelName+"\" on server \""+args[0]+"\"...");
			EventServerChannelAdmin channelAdmin2 = EventServerChannelAdminHelper
					.narrow(ncRef.resolve_str(args[0]));
			long id2 = channelAdmin2.createChannel(injectorSystemChannelName, 3);*/
			System.out.println("Done.");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CAAgentProcess(args);
	}

}
