package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class Channel {

	private String topic;
	
	private int capacity;
	
	Vector<Event> events = new Vector<Event>();

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
