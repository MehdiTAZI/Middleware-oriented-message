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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.servercommons.CORBAUtils;



/**
 * Classe ChannelPublisher
 * 
 * To publish or remove the Channel Admin in the ORB
 * 
 * UC n°: US14,US15 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */
public class ChannelPublisher {
	private static Logger log = LoggerFactory.getLogger(ChannelPublisher.class);

    private static Map<ChannelAdminImpl, byte[]> oidMap = Collections
	    .synchronizedMap(new HashMap<ChannelAdminImpl, byte[]>());

    private static POA poa;

    private static synchronized POA getPOA() {
    	
	try {
	    if (poa == null) {
		poa = POAHelper.narrow(BFFactory.getOrb()
			.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
	    }

	} catch (InvalidName e) {
	    log.error("Name not valid",e);
	} catch (AdapterInactive e) {
	   log.error("Adapter inactive",e);
	}
	log.debug("POA accessed");
	return poa;
    }

    /**
     * Publish a ChannelAdminService on the ORB and register it on the Name
     * Service with the Channel Name (Topic).
     * 
     * @param channelAdminImpl
     *            the implementation of ChannelAdmin
     */
    public static void publish(ChannelAdminImpl channelAdminImpl) {

//	ThreadEvent te = new ThreadEvent(channelAdminImpl.getTopic());
//	thread = new Thread(te);
//	thread.start();
	try {
	    NamingContextExt nc = NamingContextExtHelper.narrow(BFFactory
		    .getOrb().resolve_initial_references("NameService"));
	    byte[] oid = getPOA().servant_to_id(
		    new ChannelAdminPOATie(channelAdminImpl));
	    oidMap.put(channelAdminImpl, oid);
	    CORBAUtils.createContext(nc, "eventChannel");
	    nc.rebind(nc.to_name("eventChannel/"+channelAdminImpl.getTopic()), getPOA()
		    .id_to_reference(oid));

	} catch (Exception e) {
	   log.error("CORBA ERROR",e);
	   
	}
	log.debug("ChannelAdminImpl of {} is published",channelAdminImpl.getTopic());
    }

    /**
     * Deactivate from the POA the ChannelAdmin and unbind it form the name
     * service.
     * 
     * @param channelAdminImpl
     */
    public static void destroy(ChannelAdminImpl channelAdminImpl) {
	try {
	    // corba object deactivation
	    byte[] oid = oidMap.get(channelAdminImpl);
	    getPOA().deactivate_object(oid);
	    // unbind
	    NamingContextExt nc = NamingContextExtHelper.narrow(BFFactory
		    .getOrb().resolve_initial_references("NameService"));
	    nc.unbind(nc.to_name("eventChannel/"+channelAdminImpl.getTopic()));
	    //remove from map
	    oidMap.remove(channelAdminImpl);
	    for(ProxyForPushConsumer ppc : channelAdminImpl.getChannelAdminctrl().getOidProxyForPushConsumerMap().keySet()){
	    	getPOA().deactivate_object(channelAdminImpl.getChannelAdminctrl().getOidProxyForPushConsumerMap().get(ppc));
	    }
	    for(ProxyForPushSupplier pps : channelAdminImpl.getChannelAdminctrl().getOidProxyForPushSupplierMap().keySet()){
	    	getPOA().deactivate_object(channelAdminImpl.getChannelAdminctrl().getOidProxyForPushSupplierMap().get(pps));
	    }
	} catch (Exception e) {
		log.error("Unable to contact NameService",e);
	}
	log.debug("Destruction of ChannelAdminImpl {} is done",channelAdminImpl.getTopic());
    }
}