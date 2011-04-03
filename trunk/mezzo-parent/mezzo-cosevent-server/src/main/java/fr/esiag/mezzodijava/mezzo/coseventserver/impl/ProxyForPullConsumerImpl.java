package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.omg.CORBA.BooleanHolder;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumerOperations;

/**
 * Classe ProxyForPullConsumerImpl
 * 
 * 
 * @author Mezzo-Team
 * 
 */

public class ProxyForPullConsumerImpl extends AbstractProxyImpl implements
	ProxyForPullConsumerOperations {

	public ProxyForPullConsumerImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	@Override
	public void connect() throws ChannelNotFoundException,
			MaximalConnectionReachedException, AlreadyConnectedException {
		channelCtr.addProxyForPullConsumerToConnectedList(this);
		System.out.println("Connect of a PULL Consumer to \""
			+ channelCtr.getChannel().getTopic() + "\".");
		
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		channelCtr.removeProxyForPullConsumerFromConnectedList(this);
		System.out.println("Disconnect of a PULL Consumer from \""
			+ channelCtr.getChannel().getTopic() + "\".");
		
	}

	@Override
	public Event pull(BooleanHolder hasEvent) throws ChannelNotFoundException,
			NotConnectedException {
		
		Event e;
		// has events in list ?
		hasEvent.value = (channelCtr.getChannel().getPendingEvents()!=0);
		
		// yes, so take it
		if(hasEvent.value==true){
			e = channelCtr.getEventForPull();
		}else{
			// call the suppliers to generate events
			e = new Event();
		}
		return e;
	}

   

}
