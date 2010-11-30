package impl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CosEvent.Canal;
import CosEvent.CanalHelper;
import CosEvent.Event;
import CosEvent.ProxyPushConsumerHelper;
import CosEvent.ProxyPushConsumerPOA;
import CosEvent.PushSupplier;

public class ProxyPushConsumerImpl extends ProxyPushConsumerPOA{

	private Canal canal;
	private PushSupplier ps;	
	private ORB orb;
	
	public ProxyPushConsumerImpl(String topic,ORB orb) {
		try {
			
		
		this.orb=orb;
		NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
		
		canal=CanalHelper.narrow(nc.resolve_str(topic));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void connect(PushSupplier ps) {
		this.ps=ps;
		try {
			

		POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();
		canal.add_consumer(ProxyPushConsumerHelper.narrow(poa.servant_to_reference(this)));
		
		} catch (Exception e) {
			// TODO: handle exception
		}		
		
	}

	@Override
	public void disconnect() {
		try {
			

			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			canal.remove_consumer(ProxyPushConsumerHelper.narrow(poa.servant_to_reference(this)));
			
			} catch (Exception e) {
				// TODO: handle exception
			}		
			

		
	}

	@Override
	public void push(Event event) {
		canal.add_event(event);
		ps.receive(event);
		
		
	}

	@Override
	public PushSupplier getSupplier() {
		// TODO Auto-generated method stub
		return ps;
	}

	
}
