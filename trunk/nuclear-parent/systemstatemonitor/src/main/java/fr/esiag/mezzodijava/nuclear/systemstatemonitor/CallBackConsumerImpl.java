package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DbEventConnector;
import fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DbEventConnectorImpl;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfoPK;

public class CallBackConsumerImpl implements CallbackConsumerOperations {
	private DbEventConnector dbConnector;
	private InjectorSystemStateSupplier supplier;
	public CallBackConsumerImpl(InjectorSystemStateSupplier supplier)
	{
		dbConnector= new DbEventConnectorImpl();/*DONT FORGET TO INSTANCIATE THIS ! */
		this.supplier=supplier;
		System.out.println("callback initialisé");
		
	}
	@Override
	public void receive(Event e) throws ConsumerNotFoundException {
		
		System.out.println("Message recu timestamp:" + e.header.timestamp
				+ ", contenu" + e.body.content);
				
		EventInfo eventInfo = new EventInfo(e);
		
		//if Event is an alert so push it.
		if (eventInfo.isAlerte())
			supplier.PushEvent(e);
		
		//persist the event on the database
		dbConnector.persist(eventInfo);
		
		/*System.out.println("Message archivé timestamp:" + e.header.timestamp
				+ ", contenu" + e.body.content);	*/
	}

}
