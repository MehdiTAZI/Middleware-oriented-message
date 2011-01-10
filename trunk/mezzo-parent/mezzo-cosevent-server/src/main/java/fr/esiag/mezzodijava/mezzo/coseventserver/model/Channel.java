package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

public class Channel {

	private String topic;	
	private int capacity;	
	private Vector<Event> events = new Vector<Event>();	
	private Set<ProxyForPushConsumerImpl> consumers=new HashSet<ProxyForPushConsumerImpl>();
	private Set<ProxyForPushSupplierImpl> suppliers=new HashSet<ProxyForPushSupplierImpl>();
	
	
	
	public Set<ProxyForPushConsumerImpl> getProxyForPushConsumers() {
		return consumers;
	}

	public void setConsumers(Set<ProxyForPushConsumerImpl> consumers) {
		this.consumers = consumers;
	}

	public Set<ProxyForPushSupplierImpl> getProxyForPushSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<ProxyForPushSupplierImpl> suppliers) {
		this.suppliers = suppliers;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}
	
	
	
	public void addEvents(Event event){
		events.add(event);
	}

}
