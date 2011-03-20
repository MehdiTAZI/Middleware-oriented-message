package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import java.util.List;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DbEventConnector;
import fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DbEventConnectorImpl;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;
import fr.esiag.nuclear.commons.model.Pression;
import fr.esiag.nuclear.commons.model.RadioActivite;
import fr.esiag.nuclear.commons.model.Temperature;

public class CallBackConsumerImpl implements CallbackConsumerOperations {

    private DbEventConnector dbConnector;
    private InjectorSystemStateSupplier supplier;

    public CallBackConsumerImpl(InjectorSystemStateSupplier supplier) {
	dbConnector = new DbEventConnectorImpl();
	/*
						  * DONT FORGET TO INSTANCIATE
						  * THIS !
						  */
	this.supplier = supplier;
	System.out.println("callback initialisé");

    }

    @Override
    public void receive(Event e) throws ConsumerNotFoundException {
    	    	
    if(e.body.type.equals("String")){
    	System.out.println("IN STRING");
	System.out.println("Message recu type=(String) timestamp:" + e.header.timetolive
		+ ", contenu " + e.body.content.extract_string());
    }
    
    if(e.body.type.equals("Temperature")){
    	System.out.println("IN TEMERATURE");
		Temperature t=(Temperature)e.body.content.extract_Value();
		System.out.println("Message recu type=(Temperature) timestamp:" + e.header.timetolive
				+ ", contenu: Temperature" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
			
	}
    
    if(e.body.type.equals("Pression")){
    	System.out.println("IN Pression");
		Pression t=(Pression)e.body.content.extract_Value();
		System.out.println("Message recu type=(Pression) timestamp:" + e.header.timetolive
				+ ", contenu: Pression" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
			
	}
    
    if(e.body.type.equals("RadioActivite")){
    	System.out.println("IN RADIOACTIVITE");
		RadioActivite t=(RadioActivite)e.body.content.extract_Value();
		System.out.println("Message recu type=(RadioActivite) timestamp:" + e.header.timetolive
				+ ", contenu: RadioActivite" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
			
	}
    
	EventInfo eventInfo = new EventInfo(e);
	int sum = 0;
	int average = 0;

	// if Event is an alert so push it, code 333 for value exceeded
	// alerts event.header.priority = 3
	if (eventInfo.isAlerte()) {
	    e.header.code = 333;
	    e.header.priority = 3;
	    supplier.PushEvent(e);
	}

	// persist the event on the database
	dbConnector.persist(eventInfo);

	// take the 5 last events of this type
	List<EventInfo> list = dbConnector.findLastFive(eventInfo.getType());
	if (list.size() > 0) {
	    for (EventInfo event : list) {
		int value = Integer.valueOf(event.getData());
		sum += value;
	    }
	    // make the average of the 5 values
	    average = sum / list.size();
	}
	// if the state has changed over 10% since last 5 events,
	// alert! code 999
	// state change event.header.priority = 2
	System.out.println("DATA"+eventInfo.getData());
	boolean state = (average - Integer.valueOf(eventInfo.getData())) > (average * 10 / 100);
	if (state) {
	    e.header.code = 999;
	    e.header.priority = 2;
	    supplier.PushEvent(e);
	}

    }

}
