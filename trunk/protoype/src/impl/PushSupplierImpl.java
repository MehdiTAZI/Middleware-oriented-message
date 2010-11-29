package impl;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CosEvent.Event;
import CosEvent.ProxyPushConsumer;
import CosEvent.PushSupplierHelper;
import CosEvent.PushSupplierPOA;




public class PushSupplierImpl extends PushSupplierPOA{

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	
	public PushSupplierImpl() {
		
		
	}
	

	
	@Override
	public void receive(Event event) {
		System.out.println("PS "+event.event());
		
	}

	
	
	

	
	
	
	
}
