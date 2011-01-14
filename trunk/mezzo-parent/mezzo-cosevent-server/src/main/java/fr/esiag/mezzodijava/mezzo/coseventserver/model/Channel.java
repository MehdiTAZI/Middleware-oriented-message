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
	private Set<ProxyForPushConsumerImpl> consumersConnected=new HashSet<ProxyForPushConsumerImpl>();
	private Set<ProxyForPushSupplierImpl> suppliersConnected=new HashSet<ProxyForPushSupplierImpl>();
	private Set<ProxyForPushConsumerImpl> consumersSubscribed=new HashSet<ProxyForPushConsumerImpl>();
	private Set<ProxyForPushSupplierImpl> suppliersSubscribed=new HashSet<ProxyForPushSupplierImpl>();
	
	public Channel(String topic){
		this.topic=topic;
	}
	
	public Set<ProxyForPushConsumerImpl> getConsumersConnected() {
		return consumersConnected;
	}

	public void setConsumersConnected(
			Set<ProxyForPushConsumerImpl> consumersConnected) {
		this.consumersConnected = consumersConnected;
	}

	public Set<ProxyForPushSupplierImpl> getSuppliersConnected() {
		return suppliersConnected;
	}

	public void setSuppliersConnected(
			Set<ProxyForPushSupplierImpl> suppliersConnected) {
		this.suppliersConnected = suppliersConnected;
	}

	public Set<ProxyForPushConsumerImpl> getConsumersSubscribed() {
		return consumersSubscribed;
	}

	public void setConsumersSubscribed(
			Set<ProxyForPushConsumerImpl> consumersSubscribed) {
		this.consumersSubscribed = consumersSubscribed;
	}

	public Set<ProxyForPushSupplierImpl> getSuppliersSubscribed() {
		return suppliersSubscribed;
	}

	public void setSuppliersSubscribed(
			Set<ProxyForPushSupplierImpl> suppliersSubscribed) {
		this.suppliersSubscribed = suppliersSubscribed;
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
	public boolean ConsumersConnectedListcapacityReached(){
		return capacity>consumersConnected.size();
	}
	public boolean SuppliersConnectedsListcapacityReached(){
		return capacity>suppliersConnected.size();
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
