package fr.esiag.mezzodijava.mezzo.libclient;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class callBackConsumerImpl implements CallbackConsumerOperations {

	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Message recu timestamp:" + evt.timestamp
				+ ", contenu" + evt.content);
	}

}