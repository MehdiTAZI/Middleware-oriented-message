package fr.esiag.mezzodijava.mezzoproto.supplier.impl;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.EventPOA;

public class EventString extends EventPOA{

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
