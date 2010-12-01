package fr.esiag.mezzodijava.mezzoproto.cosevent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.EventPOA;
@Entity(name="EventString")
public class EventString extends EventPOA{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Event")
	private String event;
	
	public EventString(String event) {
		this.event=event;
	}

	@Override
	public String message() {
		
		return event;
	}

	@Override
	public void message(String event) {
		this.event=event;
		
	}

	
	
	
}
