package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

/**
 * Classe ChannelAdminCtr
 * 
 * For Proxy creation : create, store in POA and return the Proxy
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public class ChannelAdminCtr {

	private ORB orb;
	private ChannelCtr channelCtr;
	

	// Constructor
	public ChannelAdminCtr(ORB orb,ChannelCtr channel) {
		this.orb=orb;
		this.channelCtr=channel;
	}	
	public ChannelAdminCtr(String channel){
		this.orb=BFFactory.createOrb(null, null);
		this.channelCtr=BFFactory.getChannelCtr(channel);
	}
	

	public ProxyForPushSupplier createProxyForPushSupplier(){
	ProxyForPushSupplierImpl proxy=null;
		
		try {					
			proxy=new ProxyForPushSupplierImpl(channelCtr);						
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();											
		    return ProxyForPushSupplierHelper.narrow(poa.servant_to_reference(new ProxyForPushSupplierPOATie(proxy)));
		
		
		} catch (Exception e) {
			return null;
		}
	}
	
	public ProxyForPushConsumer createProxyForPushConsumer(){
		ProxyForPushConsumerImpl proxy=null;
		
		try {
			proxy=new ProxyForPushConsumerImpl(channelCtr);
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();									
		
			return ProxyForPushConsumerHelper.narrow(poa.servant_to_reference(new ProxyForPushConsumerPOATie(proxy)));
		
		} catch (Exception e) {
			return null;
		}
	}

}
