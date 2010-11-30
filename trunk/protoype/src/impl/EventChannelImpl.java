package impl;


import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CosEvent.EventChannelPOA;
import CosEvent.ProxyPushConsumer;
import CosEvent.ProxyPushConsumerHelper;
import CosEvent.ProxyPushSupplier;
import CosEvent.ProxyPushSupplierHelper;




public class EventChannelImpl extends EventChannelPOA{

	private ORB orb;
	private ProxyPushConsumer ppc;
	private ProxyPushSupplier pps;
	
	
	
	public EventChannelImpl(ORB orb){
		this.orb=orb;		
	}
	
	public ProxyPushConsumer get_Proxy_Push_Consumer() {
		
		ProxyPushConsumerImpl proxy=null;
			
		try {
			
		
			proxy=new ProxyPushConsumerImpl("MEZZO1",orb);
			
			
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
									
		System.out.println(orb.object_to_string(poa.servant_to_reference(proxy)));
		
		return ProxyPushConsumerHelper.narrow(poa.servant_to_reference(proxy));
		
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public ProxyPushSupplier get_Proxy_Push_Supplier() {
		
		ProxyPushSupplierImpl proxy=null;
		
		try {
			
		
			proxy=new ProxyPushSupplierImpl("MEZZO2",orb);
			
			
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
									
			System.out.println(orb.object_to_string(poa.servant_to_reference(proxy)));	
		return ProxyPushSupplierHelper.narrow(poa.servant_to_reference(proxy));
		
		} catch (Exception e) {
			return null;
		}
	}

}
