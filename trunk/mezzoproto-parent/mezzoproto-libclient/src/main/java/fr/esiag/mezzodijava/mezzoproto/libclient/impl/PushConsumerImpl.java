package fr.esiag.mezzodijava.mezzoproto.libclient.impl;


import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushConsumerPOA;


public class PushConsumerImpl extends PushConsumerPOA{

	private String name;

	@Override
	public void disconnect() {
		System.out.println("le consumer "+ name+ "est deconnect�");
		
	}

	public PushConsumerImpl(String name) {
			this.name=name;
	}
	
	
	public void push(Event event) {
		
		System.out.println("le consumer "+ name+ " � recu le message { "+ event.message()+" }");
		
	}

	

}
