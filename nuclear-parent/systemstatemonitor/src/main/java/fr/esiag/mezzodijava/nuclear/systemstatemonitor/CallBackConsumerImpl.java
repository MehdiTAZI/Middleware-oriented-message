package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DbEventConnector;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;

public class CallBackConsumerImpl implements CallbackConsumerOperations {
	private DbEventConnector dbConnector;
	private InjectorSystemStateSupplier supplier;
	public CallBackConsumerImpl(InjectorSystemStateSupplier supplier)
	{
		//dbConnector= new dbConnectorImpl();/*DONT FORGET TO INSTANCIATE THIS ! */
		this.supplier=supplier;
		
	}
	@Override
	public void receive(Event e) throws ConsumerNotFoundException {
		
		System.out.println("Message recu timestamp:" + e.timestamp
				+ ", contenu" + e.content);
				
		EventInfo eventInfo = new EventInfo(e);
		
		//if Event is an alert so push it.
		if (eventInfo.isAlerte())
			supplier.PushEvent(e);
		
		int eventCode = eventInfo.getCode();
		String eventType = eventInfo.getType();
		
		Event stortedEvent= dbConnector.find(eventCode,eventType);
		
		if (stortedEvent!=null)
		{
			EventInfo storedEventInfo = new EventInfo(stortedEvent);
			if(! storedEventInfo.getData().equals(eventInfo.getData()))
			{
				supplier.PushEvent(e);
			}
		}
		else//if it's the first time that we receive this event
		{
			//So Push this event !
			supplier.PushEvent(e);
		}
			
		//persist the event on the database
		dbConnector.persist(e);
		
		System.out.println("Message archivé timestamp:" + e.timestamp
				+ ", contenu" + e.content);

		
	}

}
