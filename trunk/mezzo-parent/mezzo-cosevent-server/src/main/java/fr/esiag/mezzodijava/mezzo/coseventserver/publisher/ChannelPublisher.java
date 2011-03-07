package fr.esiag.mezzodijava.mezzo.coseventserver.publisher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ThreadEvent;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;

public class ChannelPublisher {
    private static Thread thread;

    private static Map<ChannelAdminImpl, byte[]> oidMap = Collections
	    .synchronizedMap(new HashMap<ChannelAdminImpl, byte[]>());

    public static POA poa;

    private static synchronized POA getPOA() {
	try {
	    if (poa == null) {
		poa = POAHelper.narrow(BFFactory.getOrb()
			.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
	    }

	} catch (InvalidName e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (AdapterInactive e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return poa;
    }

    public static void publish(ChannelAdminImpl channelAdminImpl) {

	ThreadEvent te = new ThreadEvent(channelAdminImpl.getTopic());
	thread = new Thread(te);
	thread.start();
	try {
	    NamingContextExt nc = NamingContextExtHelper.narrow(BFFactory
		    .getOrb().resolve_initial_references("NameService"));
	    byte[] oid = getPOA().servant_to_id(new ChannelAdminPOATie(
		    channelAdminImpl));
	    oidMap.put(channelAdminImpl, oid);
	    nc.rebind(nc.to_name(channelAdminImpl.getTopic()),
		    getPOA().id_to_reference(oid));

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void destroy(ChannelAdminImpl channelAdminImpl) {
	try {
	    //corba object deactivation
	    byte[] oid = oidMap.get(channelAdminImpl);
	    getPOA().deactivate_object(oid);
	    //unbind
	    NamingContextExt nc = NamingContextExtHelper.narrow(BFFactory
		    .getOrb().resolve_initial_references("NameService"));
	    nc.unbind(nc.to_name(channelAdminImpl.getTopic()));
	} catch (Exception e) {
	    System.out.println("Impossible de contacter le name service");
	}
    }
}