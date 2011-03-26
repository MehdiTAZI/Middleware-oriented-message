package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.RandomChannelIdentifier;

@Entity
@Table(name = "CHANNEL")
public class ChannelModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	private List<ConsumerModel> consumers;
	
	private Set<ProxyForPushConsumerImpl> consumersConnected = new HashSet<ProxyForPushConsumerImpl>();

    private Map<ProxyForPushConsumerImpl, SortedSet<Event>> consumersSubscribed = Collections
	    .synchronizedMap(new HashMap<ProxyForPushConsumerImpl, SortedSet<Event>>());

    private Set<ProxyForPushSupplierImpl> suppliersConnected = new HashSet<ProxyForPushSupplierImpl>();

    private Comparator<Event> comparator = new PriorityEventComparator();
	

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getTopic() {
	    return topic;
	}

	public void setTopic(String topic) {
	    this.topic = topic;
	}

	public List<EventModel> getEvents() {
	    return events;
	}

	public void setEvents(List<EventModel> events) {
	    this.events = events;
	}

	public List<ConsumerModel> getConsumers() {
	    return consumers;
	}

	public void setConsumers(List<ConsumerModel> consumers) {
	    this.consumers = consumers;
	}

	public int getConnectionCapacity() {
	    return connectionCapacity;
	}

	public void setConnectionCapacity(int connectionCapacity) {
	    this.connectionCapacity = connectionCapacity;
	}
	
	

}
