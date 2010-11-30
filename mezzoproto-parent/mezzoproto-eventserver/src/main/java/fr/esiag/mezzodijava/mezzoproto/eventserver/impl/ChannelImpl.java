package fr.esiag.mezzodijava.mezzoproto.eventserver.impl;





import java.util.Vector;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelPOA;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumer;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplier;



public class ChannelImpl extends ChannelPOA{

	private Vector<ProxyPushSupplier> ppSuppliers; 
	private Vector<ProxyPushConsumer> ppConsumers;	
	private String topic;
	private int capacity;	
	private Vector<Event> events;
	
	
	public ChannelImpl(String topic,int capacity) {
		
		this.capacity=capacity;
		this.topic=topic;
		ppConsumers=new Vector<ProxyPushConsumer>(capacity);
		ppSuppliers=new Vector<ProxyPushSupplier>(capacity);				
		events=new Vector<Event>(200);
		
	}

	@Override
	public void add_consumer(ProxyPushConsumer ppc) {
		
		System.out.println("ADD_SUPPLIER");
		if(!ppConsumers.contains(ppc))
			ppConsumers.add(ppc);
		
	}

	@Override
	public void add_event(Event event) {		
		System.out.println("ADD_EVENt "+ event.message());
		events.add(event);
		
		for (ProxyPushSupplier s:ppSuppliers) {	
			System.out.println("APPEL ADD_EVENt");
			s.receive(event);
			
		}
		
	}
	
	public void add_supplier(ProxyPushSupplier pps) {
		System.out.println("ADD_CONSUMER");
		if(!ppSuppliers.contains(pps))
			ppSuppliers.add(pps);
		
	}
	
	public void remove_consumer(ProxyPushConsumer ppc) {
		ppConsumers.remove(ppc);
		
	}
	
	public void remove_supplier(ProxyPushSupplier pps) {
		ppSuppliers.remove(pps);
		
	}
	
}
