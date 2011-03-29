package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.RandomChannelIdentifier;

@Entity
@Table(name = "CHANNEL")
public class Channel implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int CAPACITY_QUEUE = 100;

	private long identifier;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "topic")
	private String topic;

	@Column(name = "connectionCapacity")
	private int connectionCapacity;

	@ElementCollection
	@CollectionTable(name = "EVENT")
	@Column(name = "events")
	private List<EventModel> events;

	@ElementCollection
	@CollectionTable(name = "CONSUMER")
	@MapKeyColumn(name="id")
	private Map<String, ConsumerModel> consumers = new HashMap<String,ConsumerModel>();

	private Map<String,ProxyForPushConsumerImpl> consumersConnected = new HashMap<String,ProxyForPushConsumerImpl>();

	private Map<String,ProxyForPushSupplierImpl> suppliersConnected = new HashMap<String,ProxyForPushSupplierImpl>();

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
		events = new ArrayList<EventModel>();

	}

	/**
	 * Add a Push Consumer to the connected list.
	 * 
	 * @param ppc
	 *            the push consumer to add.
	 */
	public void addSubscribedConsumer(String idConsumer) {
		ConsumerModel nouveau = new ConsumerModel();
		nouveau.setIdConsumer(idConsumer);
		this.consumers.put(nouveau.getIdConsumer(), nouveau);
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
		return connectionCapacity == consumersConnected.size();
	}

	/**
	 * is connection capacity reached for connected suppliers ?
	 * 
	 * @return true if the list is full.
	 */
	public boolean isSuppliersConnectedsListcapacityReached() {
		return connectionCapacity == suppliersConnected.size();
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


	public void setConsumers(Map <String,ConsumerModel> consumers) {
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
	public void addEvent(EventModel em) {
		events.add(em);
	}

	public int getConnectionCapacity() {
		return connectionCapacity;
	}

	public void setConnectionCapacity(int connectionCapacity) {
		this.connectionCapacity = connectionCapacity;
	}

	public List<EventModel> getEvents() {
		return events;
	}

	public void setEvents(List<EventModel> events) {
		this.events = events;
	}
	
	
}
