package fr.esiag.mezzodijava.mezzoproto.libclient.impl;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushSupplierPOA;




public class PushSupplierImpl extends PushSupplierPOA{

	private String name;
	
	public PushSupplierImpl(String name) {
		this.name=name;
	}
	
	
	public void receive(Event event) {
		System.out.println("le supplier "+name+" � envoy� le message envoy� est : "+event.message());
		
	}
	
	@Override
	public void disconnect() {
		
		System.out.println("le supplier "+ name+ " est deconnect�");
	}

	
}
