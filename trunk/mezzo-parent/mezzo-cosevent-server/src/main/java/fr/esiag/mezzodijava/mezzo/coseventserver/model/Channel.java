package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.callback.Callback;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

/**
 * Classe Channel
 * 
 * The Channel model
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public class Channel {

	private String topic;	
	private int capacity;	
	List<Event> events = Collections.synchronizedList(new ArrayList<Event>());
	// private Vector<Event> events = new Vector<Event>();	
	private Map<CallbackConsumer, List<Event>> consumersSubscribed = Collections.synchronizedMap(new HashMap<CallbackConsumer, List<Event>>());
	private Set<ProxyForPushConsumerImpl> consumersConnected= Collections.synchronizedSet(new HashSet<ProxyForPushConsumerImpl>());
	private Set<ProxyForPushSupplierImpl> suppliersConnected=Collections.synchronizedSet(new HashSet<ProxyForPushSupplierImpl>());
	
	public Channel(String topic){
		this.topic=topic;
	}
	
	public Set<ProxyForPushConsumerImpl> getProxyForPushConsumers() {
		return consumersConnected;
	}

	public void setConsumers(Set<ProxyForPushConsumerImpl> consumers) {
		this.consumersConnected = consumers;
	}

	public Set<ProxyForPushSupplierImpl> getProxyForPushSuppliers() {
		return suppliersConnected;
	}

	public void setSuppliers(Set<ProxyForPushSupplierImpl> suppliers) {
		this.suppliersConnected = suppliers;
	}
	
	public Map<CallbackConsumer, List<Event>> getConsumersSubscribed() {
		return consumersSubscribed;
	}

	public void setConsumersSubscribed(
			Map<CallbackConsumer, List<Event>> consumersSubscribed) {
		this.consumersSubscribed = consumersSubscribed;
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

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void addEvents(Event event){
		events.add(event);
		for(Event e:events)
			System.out.println("AddEvent in Channel "+e);
	}

}
