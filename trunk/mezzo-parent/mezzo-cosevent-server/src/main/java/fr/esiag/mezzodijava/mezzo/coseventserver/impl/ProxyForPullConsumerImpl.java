package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumerOperations;

/**
 * Classe ProxyForPullConsumerImpl
 * 
 * 
 * @author Mezzo-Team
 * 
 */

public class ProxyForPullConsumerImpl extends AbstractProxyImpl implements
	MessageListener, ProxyForPullConsumerOperations {

	public ProxyForPullConsumerImpl(String topic, String idComponent) {
		super(topic, idComponent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect() throws ChannelNotFoundException,
			MaximalConnectionReachedException, AlreadyConnectedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pull() throws ChannelNotFoundException, NotConnectedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		// TODO Auto-generated method stub
		
	}

   

}
