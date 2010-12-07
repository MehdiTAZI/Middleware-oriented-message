package fr.esiag.mezzodijava.mezzoproto.eventserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="EventString")
public class EventString{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Event")
	private String event;
	
	public EventString(String event) {
		this.event=event;
	}

	public String message() {
		
		return event;
	}

	public void message(String event) {
		this.event=event;
		
	}

	
	
	
}
