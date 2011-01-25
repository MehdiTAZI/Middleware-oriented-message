package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private int capacity;
    private Set<ProxyForPushConsumerImpl> consumersConnected = new HashSet<ProxyForPushConsumerImpl>();

    private Map<ProxyForPushConsumerImpl, List<Event>> consumersSubscribed = Collections
	    .synchronizedMap(new HashMap<ProxyForPushConsumerImpl, List<Event>>());
    private Set<ProxyForPushSupplierImpl> suppliersConnected = new HashSet<ProxyForPushSupplierImpl>();
    private Set<ProxyForPushSupplierImpl> suppliersSubscribed = new HashSet<ProxyForPushSupplierImpl>();
    private String topic;

    public Channel(String topic, int capacity) {
	this.topic = topic;
	this.capacity = capacity;
    }

    public void addSubscribedConsumer(ProxyForPushConsumerImpl ppc) {
	this.consumersSubscribed.put(ppc,
		Collections.synchronizedList(new ArrayList<Event>()));
    }

    public int getCapacity() {
	return capacity;
    }

    public Set<ProxyForPushConsumerImpl> getConsumersConnected() {
	return consumersConnected;
    }

    public Map<ProxyForPushConsumerImpl, List<Event>> getConsumersSubscribed() {
	return consumersSubscribed;
    }

    public Set<ProxyForPushSupplierImpl> getSuppliersConnected() {
	return suppliersConnected;
    }

    public Set<ProxyForPushSupplierImpl> getSuppliersSubscribed() {
	return suppliersSubscribed;
    }

    public String getTopic() {
	return topic;
    }

    public boolean isConsumersConnectedListcapacityReached() {
	return capacity == consumersConnected.size();
    }

    public boolean isSuppliersConnectedsListcapacityReached() {
	return capacity == suppliersConnected.size();
    }

    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

    public void setConsumersConnected(
	    Set<ProxyForPushConsumerImpl> consumersConnected) {
	this.consumersConnected = consumersConnected;
    }

    public void setConsumersSubscribed(
	    Map<ProxyForPushConsumerImpl, List<Event>> consumersSubscribed) {
	this.consumersSubscribed = consumersSubscribed;
    }

    public void setSuppliersConnected(
	    Set<ProxyForPushSupplierImpl> suppliersConnected) {
	this.suppliersConnected = suppliersConnected;
    }

    public void setSuppliersSubscribed(
	    Set<ProxyForPushSupplierImpl> suppliersSubscribed) {
	this.suppliersSubscribed = suppliersSubscribed;
    }

    public void setTopic(String topic) {
	this.topic = topic;
    }

}
