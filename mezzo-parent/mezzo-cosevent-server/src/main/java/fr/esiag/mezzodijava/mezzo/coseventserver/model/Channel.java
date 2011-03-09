package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.RandomChannelIdentifier;

/**
 * Classe Channel : contain subscribed and connected list of consumer and
 * supplier.
 * 
 * Events are stored with each subscribed consumers in the channel and stay
 * until they are delivered.
 * 
 * The Channel model
 * 
 * UC n°: US14,US15 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */

public class Channel {

    private final int CAPACITY_QUEUE = 100;

    private int capacity;
    private long identifier;
    private Set<ProxyForPushConsumerImpl> consumersConnected = new HashSet<ProxyForPushConsumerImpl>();

    private Map<ProxyForPushConsumerImpl, SortedSet<Event>> consumersSubscribed = Collections
	    .synchronizedMap(new HashMap<ProxyForPushConsumerImpl, SortedSet<Event>>());

    private Set<ProxyForPushSupplierImpl> suppliersConnected = new HashSet<ProxyForPushSupplierImpl>();

    private String topic;

    private Comparator<Event> comparator = new PriorityEventComparator();

    private PriorityBlockingQueue<Event> queueEvents;

    /**
     * Guive a Channel entity with <code>topic</code> and Connection
     * <code>capacity</code>.
     * 
     * @param topic
     *            String name of the Channel
     * @param capacity
     *            Capacity in connection of suppliers or consumers
     */
    public Channel(String topic, int capacity) {
	this.topic = topic;
	this.capacity = capacity;
	this.identifier = RandomChannelIdentifier.getUniqueIdentifier();
	queueEvents = new PriorityBlockingQueue<Event>(CAPACITY_QUEUE,
		comparator);

    }

    /**
     * Add a Push Consumer to the connected list.
     * 
     * @param ppc
     *            the push consumer to add.
     */
    public void addSubscribedConsumer(ProxyForPushConsumerImpl ppc) {
	this.consumersSubscribed.put(ppc, Collections
		.synchronizedSortedSet(new TreeSet<Event>(comparator)));
    }

    /**
     * Get Channel Maximal Connection number.
     * 
     * @return Maximal Connection number
     */
    public int getCapacity() {
	return capacity;
    }

    /**
     * Push Consumers connected.
     * 
     * @return set of ProxyPushConsumerImpl
     */
    public Set<ProxyForPushConsumerImpl> getConsumersConnected() {
	return consumersConnected;
    }

    /**
     * Map with key as ProxyPushConsumer and valu a List of events.
     * 
     * While nnot delivered to its consumer, an event stay in its list.
     * 
     * @return.
     */
    public Map<ProxyForPushConsumerImpl, SortedSet<Event>> getConsumersSubscribed() {
	return consumersSubscribed;
    }

    /**
     * Push Suppliers connected.
     * 
     * @return set of ProxyForPushSupplierImpl
     */
    public Set<ProxyForPushSupplierImpl> getSuppliersConnected() {
	return suppliersConnected;
    }

    /**
     * Character string unique identifier of the Channel.
     * 
     * @return topic
     */
    public String getTopic() {
	return topic;
    }

    /**
     * is connection capacity reached for connected consumers ?
     * 
     * @return true if the list is full.
     */
    public boolean isConsumersConnectedListcapacityReached() {
	return capacity == consumersConnected.size();
    }

    /**
     * is connection capacity reached for connected suppliers ?
     * 
     * @return true if the list is full.
     */
    public boolean isSuppliersConnectedsListcapacityReached() {
	return capacity == suppliersConnected.size();
    }

    /**
     * Define Channel Max connection number.
     * 
     * @param capacity
     *            max nb of connected consumer and suppliers.
     */
    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

    /**
     * Getter of the set of connected push consumer.
     * 
     * For persistance purpose only.
     * 
     * @param consumersConnected
     */
    public void setConsumersConnected(
	    Set<ProxyForPushConsumerImpl> consumersConnected) {
	this.consumersConnected = consumersConnected;
    }

    /**
     * Getter of the map of subscribed push consumer.
     * 
     * For persistance purpose only.
     * 
     * @param consumersSubscribed
     */
    public void setConsumersSubscribed(
	    Map<ProxyForPushConsumerImpl, SortedSet<Event>> consumersSubscribed) {
	this.consumersSubscribed = consumersSubscribed;
    }

    /**
     * Getter of the set of connected push supplier.
     * 
     * For persistance purpose only.
     * 
     * @param suppliersConnected
     */
    public void setSuppliersConnected(
	    Set<ProxyForPushSupplierImpl> suppliersConnected) {
	this.suppliersConnected = suppliersConnected;
    }

    /**
     * Set the Channel Topic.
     * 
     * @param topic
     *            the topic.
     */
    public void setTopic(String topic) {
	this.topic = topic;
    }

    public long getIdentifier() {
	return identifier;
    }

    public void setIdentifier(long identifier) {
	this.identifier = identifier;
    }

    /**
     * Add en event in the queue.
     * 
     * @param event
     *            an Event.
     */
    public void addEvent(Event event) {
	queueEvents.add(event);

    }

    public PriorityBlockingQueue<Event> getQueueEvents() {
	// System.out.println("N getQueueEvents "+queueEvents.size());
	return queueEvents;
    }

    public void setQueueEvents(PriorityBlockingQueue<Event> listeEvents) {
	this.queueEvents = listeEvents;
    }

}
