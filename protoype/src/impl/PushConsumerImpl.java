package impl;


import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;



import CosEvent.Event;
import CosEvent.EventHelper;
import CosEvent.MessageListener;
import CosEvent.ProxyPushConsumer;
import CosEvent.ProxyPushSupplier;
import CosEvent.PushConsumer;
import CosEvent.PushConsumerHelper;
import CosEvent.PushConsumerPOA;
import CosEvent.PushSupplierHelper;


public class PushConsumerImpl extends PushConsumerPOA{

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	public PushConsumerImpl() {
		
	}
	
	@Override
	public void push(Event event) {
		// TODO Auto-generated method stub
		
	}

	

}
