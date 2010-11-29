package impl;

import CosEvent.EventPOA;

public class EventString extends EventPOA{

	private String event;
	
	public EventString(String event) {
		this.event=event;
	}

	@Override
	public String event() {
		
		return event;
	}

	@Override
	public void event(String newEvent) {
		this.event=event;
		
	}
	
	
}
