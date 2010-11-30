package main;

import impl.EventChannelImpl;
import impl.EventString;
import impl.PushConsumerImpl;
import impl.PushSupplierImpl;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CosEvent.EventHelper;
import CosEvent.ProxyPushConsumer;
import CosEvent.ProxyPushSupplier;
import CosEvent.PushConsumerHelper;
import CosEvent.PushSupplier;
import CosEvent.PushSupplierHelper;

public class MainConsumer extends Thread{

	/**
	 * @param args
	 */
	public MainConsumer() {
		run();
	}
	
	public static void main(String[] args) {
		new MainConsumer();

	}

	@Override
	public void run() {

		
		try {
			
			ORB orb=ORB.init(args, null);
			
			PushSupplierImpl ps=new PushSupplierImpl();
			
			EventChannelImpl ec=new EventChannelImpl(orb);
			
			ProxyPushConsumer ppc=ec.get_Proxy_Push_Consumer();
			POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			ppc.connect(PushSupplierHelper.narrow(poa.servant_to_reference(ps)));
			System.out.println("Consumer est abonné au Canal MEZZO");		
			orb.run();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
