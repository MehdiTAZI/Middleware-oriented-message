package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@CollectionTable(name = "channelevents")  
	@Column(name = "events")  
	List<EventModel> events;
	
	@ElementCollection  
	@CollectionTable(name = "CONSUMER")   
	List<ConsumerModel> consumers;

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
