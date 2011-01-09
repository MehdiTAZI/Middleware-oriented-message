package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierHelper;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

/**
 * @author fab
 *
 */

public class ChannelAdminCtr {

	/**
	 * 
	 */

	private ORB orb;
	private ProxyForPushConsumerImpl ppc;
	private ProxyForPushSupplierImpl pps;
	

	public ChannelAdminCtr(ORB orb) {
		this.orb=orb;
	}	
	
	

	public ProxyForPushSupplier createProxyForPushSupplier(String topic){
	ProxyForPushSupplierImpl proxy=null;
		
		try {					
			proxy=new ProxyForPushSupplierImpl(topic);						
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();											
		//return ProxyForPushSupplierHelper.narrow(poa.servant_to_reference(proxy));
			return null;
		
		} catch (Exception e) {
			return null;
		}
	}
	
	public ProxyForPushConsumer createProxyForPushConsumer(String topic){
		ProxyForPushConsumerImpl proxy=null;
		
		try {				
			proxy=new ProxyForPushConsumerImpl(topic);						
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();									
		
			return ProxyForPushConsumerHelper.narrow(poa.servant_to_reference(proxy));
		
		} catch (Exception e) {
			
			return null;
		}
	}

}
