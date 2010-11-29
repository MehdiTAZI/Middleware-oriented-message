package impl;





import java.util.Vector;

import CosEvent.CanalPOA;
import CosEvent.Event;
import CosEvent.ProxyPushConsumer;
import CosEvent.ProxyPushSupplier;



public class CanalImpl extends CanalPOA{

	private Vector<ProxyPushSupplier> ppSuppliers;
	private Vector<ProxyPushConsumer> ppConsumers;
	private Vector<Event> events;
	private String topic;
	private int capacity;
	
	public CanalImpl(String topic,int capacity) {
		this.capacity=capacity;
		ppConsumers=new Vector<ProxyPushConsumer>(capacity);
		ppSuppliers=new Vector<ProxyPushSupplier>(capacity);
		events=new Vector<Event>();
		
	}

	@Override
	public void add_consumer(ProxyPushConsumer ppc) {
		
		ppConsumers.add(ppc);
		System.out.println("ppConsumers  " + ppConsumers.size());
		
	}

	@Override
	public void add_event(Event event) {
		
		events.add(event);
		System.out.println(event.event());
		
		
		for (ProxyPushConsumer c:ppConsumers) {
			c.getSupplier().receive(event);
		}
		
	}

	@Override
	public void add_supplier(ProxyPushSupplier pps) {
		
		
		ppSuppliers.add(pps);
		System.out.println("ppSuppliers  " + ppSuppliers.size());
	}

	@Override
	public void remove_consumer(ProxyPushConsumer ppc) {
		ppConsumers.remove(ppc);
		
	}

	@Override
	public void remove_supplier(ProxyPushSupplier pps) {
		ppSuppliers.remove(pps);
		
	}
	
}
