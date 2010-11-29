package main;
import impl.CanalImpl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;





public class CanalServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
		
		ORB orb=ORB.init(args, null);
		POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
		CanalImpl canal=new CanalImpl("MEZZO1", 5);
		CanalImpl canal2=new CanalImpl("MEZZO2", 5);
		nc.rebind(nc.to_name("MEZZO1"), poa.servant_to_reference(canal));
		nc.rebind(nc.to_name("MEZZO2"), poa.servant_to_reference(canal2));
		System.out.println("canal MEZZO is running...");
		orb.run();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
