/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.main;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

/**
 * @author Administrateur
 * 
 *-ORBInitRef NameService=corbaloc::127.0.0.1:1050/NameService
 *-Djacorb.home=C:\\mezzodev\\jacorb-2.3.1
 *-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 *-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 *
 *-Djava.endorsed.dirs=${env_var:JACORB_HOME}/lib
 */

public class CosEventServer {

	/**
	 * 
	 */
	private ORB orb;
	private String channelName="MEZZO";
	
	public CosEventServer(String[] args) {
		// TODO Auto-generated constructor stub
		
		orb=ORB.init(args,null);
		Channel channelModel = new Channel();
		ChannelCtr channelCtr=new ChannelCtr(channelModel);		
		ChannelAdminCtr channelAdminCtr=new ChannelAdminCtr(orb,channelCtr);
		ChannelAdminImpl channelAdminImpl=new ChannelAdminImpl(channelAdminCtr);
		channelModel.setTopic(channelName);
		
		try {			
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			
						
			nc.rebind(nc.to_name(channelName), poa.servant_to_reference(new ChannelAdminPOATie(channelAdminImpl)));
			System.out.println("Server is running...");
			orb.run();
			
			
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServantNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterInactive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ChannelAdminCtr channelCtr = new ChannelAdminCtr();
		//ChannelAdminImpl channelAdminImpl = new ChannelAdminImpl(channelCtr);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CosEventServer(args);

	}

}
