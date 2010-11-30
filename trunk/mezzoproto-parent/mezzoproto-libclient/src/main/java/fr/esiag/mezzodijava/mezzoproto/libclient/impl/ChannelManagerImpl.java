package fr.esiag.mezzodijava.mezzoproto.libclient.impl;


import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelManagerPOA;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumer;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumerHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplier;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplierHelper;




public class ChannelManagerImpl extends ChannelManagerPOA{

	private ORB orb;
	private ProxyPushConsumer ppc;
	private ProxyPushSupplier pps;
	
	
	public ChannelManagerImpl(ORB orb){
		this.orb=orb;		
	}
	
	public ProxyPushConsumer get_Proxy_Push_Consumer(String topic) {
	
		ProxyPushConsumerImpl proxy=null;
			
		try {				
			proxy=new ProxyPushConsumerImpl(topic,orb);						
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();									
		
			return ProxyPushConsumerHelper.narrow(poa.servant_to_reference(proxy));
		
		} catch (Exception e) {
			System.out.println("erreur : "+e.getMessage());
			return null;
		}
		
	}

	@Override
	public ProxyPushSupplier get_Proxy_Push_Supplier(String topic) {
		
		ProxyPushSupplierImpl proxy=null;
		
		try {					
			proxy=new ProxyPushSupplierImpl(topic,orb);						
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();											
		return ProxyPushSupplierHelper.narrow(poa.servant_to_reference(proxy));
		
		} catch (Exception e) {
			System.out.println("erreur : "+e.getMessage());
			return null;
		}
	}

	
}
