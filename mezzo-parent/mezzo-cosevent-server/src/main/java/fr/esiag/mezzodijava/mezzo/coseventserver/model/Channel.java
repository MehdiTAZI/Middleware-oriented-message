package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.RandomChannelIdentifier;

public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;


    private long identifier;

    private int id;

    private String topic;

    private int connectionCapacity;

    private Map<String, ConsumerModel> consumers = Collections
    .synchronizedMap(new HashMap<String, ConsumerModel>());
    
    private Map<String, ConsumerModel> consumersPull = Collections
    .synchronizedMap(new HashMap<String, ConsumerModel>());

    private Map<String, ProxyForPushConsumerImpl> consumersConnected = new HashMap<String, ProxyForPushConsumerImpl>();
    private Map<String, ProxyForPushSupplierImpl> suppliersConnected = new HashMap<String, ProxyForPushSupplierImpl>();
    private Map<String, ProxyForPullSupplierImpl> suppliersPullConnected = new HashMap<String, ProxyForPullSupplierImpl>();
    
    /**
     * constructor
     */
    public Channel() {
	super();
    }

    /**
     * Give a Channel entity with <code>topic</code> and Connection
     * <code>capacity</code>.
     * 
     * @param topic
     *            String name of the Channel
     * @param capacity
     *            Capacity in connection of suppliers or consumers
     */
    public Channel(String topic, int connectionCapacity) {
	this.topic = topic;
	this.connectionCapacity = connectionCapacity;
	this.identifier = RandomChannelIdentifier.getUniqueIdentifier();
	//events.clear();

    }

    /**
     * Add a Push Consumer to the subscribed list.
     * 
     * @param ppc
     *            the push consumer to add.
     */
    public ConsumerModel addSubscribedConsumer(String idConsumer) {
	ConsumerModel nouveau = new ConsumerModel();
	nouveau.setIdConsumer(idConsumer);
	nouveau.setChannel(this);
	this.consumers.put(nouveau.getIdConsumer(), nouveau);
	return nouveau;
    }
    
    /**
     * Add a Pull Consumer to the connected list.
     * 
     * @param ppc
     *            the pull consumer to add.
     */
    public ConsumerModel addPullConsumer(String idConsumer) {
	ConsumerModel nouveau = new ConsumerModel();
	nouveau.setIdConsumer(idConsumer);
	nouveau.setChannel(this);
	this.consumersPull.put(nouveau.getIdConsumer(), nouveau);
	return nouveau;
    }

    /**
     * Get Channel Maximal Connection number.
     * 
     * @return Maximal Connection number
     */
    public int getCapacity() {
	return connectionCapacity;
    }

    /**
     * Push Consumers connected.
     * 
     * @return set of ProxyPushConsumerImpl
     */
    public Map<String, ProxyForPushConsumerImpl> getConsumersConnected() {
	return consumersConnected;
    }

    /**
     * Push Suppliers connected.
     * 
     * @return set of ProxyForPushSupplierImpl
     */
    public Map<String, ProxyForPushSupplierImpl> getSuppliersConnected() {
	return suppliersConnected;
    }
    
    /**
     * Pull Suppliers connected.
     * 
     * @return set of ProxyForPullSupplierImpl
     */
    public Map<String, ProxyForPullSupplierImpl> getSuppliersPullConnected() {
	return suppliersPullConnected;
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
	return connectionCapacity == consumersConnected.size()+consumersPull.size();
    }

    /**
     * is connection capacity reached for connected suppliers ?
     * 
     * @return true if the list is full.
     */
    public boolean isSuppliersConnectedsListcapacityReached() {
	return connectionCapacity == suppliersConnected.size()+suppliersPullConnected.size();
    }

    /**
     * Define Channel Max connection number.
     * 
     * @param capacity
     *            max nb of connected consumer and suppliers.
     */
    public void setCapacity(int capacity) {
	this.connectionCapacity = capacity;
    }

    /**
     * Getter of the set of connected push consumer.
     * 
     * For persistance purpose only.
     * 
     * @param consumersConnected
     */
    public void setConsumersConnected(
	    Map<String, ProxyForPushConsumerImpl> consumersConnected) {
	this.consumersConnected = consumersConnected;
    }

    /**
     * Getter of the set of subscribed push consumer.
     * 
     * For persistance purpose only.
     * 
     * @param consumersSubscribed
     */
    public Map<String, ConsumerModel> getConsumers() {
	return consumers;
    }
    
    /**
     * Getter of the set of subscribed pull consumer.
     * 
     * For persistance purpose only.
     * 
     * @param consumersSubscribed
     */
    public Map<String, ConsumerModel> getConsumersPull() {
	return consumersPull;
    }

    public void setConsumers(Map<String, ConsumerModel> consumers) {
	this.consumers = consumers;
    }

    /**
     * Getter of the set of connected push supplier.
     * 
     * For persistance purpose only.
     * 
     * @param suppliersConnected
     */
    public void setSuppliersConnected(
	    Map<String, ProxyForPushSupplierImpl> suppliersConnected) {
	this.suppliersConnected = suppliersConnected;
    }
    
    /**
     * Getter of the set of connected pull supplier.
     * 
     * For persistance purpose only.
     * 
     * @param suppliersPullConnected
     */
    public void setSuppliersPullConnected(
	    Map<String, ProxyForPullSupplierImpl> suppliersPullConnected) {
	this.suppliersPullConnected = suppliersPullConnected;
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

    /**
     * Getter of the ChannelIdentifier
     * @return
     */
    public long getIdentifier() {
	return identifier;
    }

    /**
     * Set the Channel Identifier
     * @param identifier
     */
    public void setIdentifier(long identifier) {
	this.identifier = identifier;
    }

    /**
     * Getter of the channel capacity
     * @return
     */
    public int getConnectionCapacity() {
	return connectionCapacity;
    }

    /**
     * Set the channel capacity
     * @param connectionCapacity
     */
    public void setConnectionCapacity(int connectionCapacity) {
	this.connectionCapacity = connectionCapacity;
    }

    /**
     * Getter of the Channel Id
     * @return
     */
	public int getId() {
		return id;
	}

	/**
	 * Set the channel Id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Set the new map of ConsumerPull
	 * @param consumersPull
	 */
	public void setConsumersPull(Map<String, ConsumerModel> consumersPull) {
		this.consumersPull = consumersPull;
	}
}
