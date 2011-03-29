package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONSUMER")
public class ConsumerModel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="idConsumer")
	private String idConsumer;
	
	@ElementCollection  
	@CollectionTable(name = "channelevents")  
	@Column(name = "events")  
	private SortedSet<EventModel> events = new TreeSet<EventModel>();

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getIdConsumer() {
	    return idConsumer;
	}

	public void setIdConsumer(String idConsumer) {
	    this.idConsumer = idConsumer;
	}

	public SortedSet<EventModel> getEvents() {
		return events;
	}

	public void setEvents(SortedSet<EventModel> events) {
		this.events = events;
	}
}
