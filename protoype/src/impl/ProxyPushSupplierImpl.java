package impl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CosEvent.Canal;
import CosEvent.CanalHelper;
import CosEvent.Event;
import CosEvent.ProxyPushSupplierHelper;
import CosEvent.ProxyPushSupplierPOA;
import CosEvent.PushConsumer;


public class ProxyPushSupplierImpl extends ProxyPushSupplierPOA{

	
	private Canal canal;
	private PushConsumer pc;	
	private ORB orb;
	
	
	public ProxyPushSupplierImpl(String topic,ORB orb) {
		try{
		this.orb=orb;
		NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
		
		canal=CanalHelper.narrow(nc.resolve_str(topic));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void connect(PushConsumer pc) {
		this.pc=pc;
		
		try {
			

		POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		poa.the_POAManager().activate();
		
		canal.add_supplier(ProxyPushSupplierHelper.narrow(poa.servant_to_reference(this)));
		
		} catch (Exception e) {
			// TODO: handle exception
		}		

		
	}

	@Override
	public void disconnect() {
		try {			
			
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			canal.remove_supplier(ProxyPushSupplierHelper.narrow(poa.servant_to_reference(this)));
			
			} catch (Exception e) {
				// TODO: handle exception
			}		

		
	}

	@Override
	public void receive(Event event) {
		System.out.println("IN PPS event "+ event.event());
		
	}

	@Override
	public PushConsumer getConsumer() {
		// TODO Auto-generated method stub
		return pc;
	}

}
