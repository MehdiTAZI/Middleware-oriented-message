package main;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import impl.EventChannelImpl;
import impl.EventString;
import impl.PushSupplierImpl;
import CosEvent.Event;
import CosEvent.EventHelper;
import CosEvent.ProxyPushConsumer;
import CosEvent.PushSupplierHelper;

public class MainSupplier extends Thread{

	
	public MainSupplier() {
		run();
	}
	public static void main(String[] args) {
		new MainSupplier();
		

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
	System.out.println("Supplier est abonné au Canal MEZZO");
	ppc.push(EventHelper.narrow(poa.servant_to_reference(new EventString("MESSAGE"))));
	orb.run();
	} catch (Exception e) {
		// TODO: handle exception
	}
}	
}
