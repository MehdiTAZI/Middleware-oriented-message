package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.omg.CORBA.BooleanHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static Logger log = LoggerFactory.getLogger(ProxyForPullConsumerImpl.class);

	public ProxyForPullConsumerImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	@Override
	public void connect() throws ChannelNotFoundException,
			MaximalConnectionReachedException, AlreadyConnectedException {
		log.debug("Connection of a Pull Consumer (idComponent {}) to {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.addProxyForPullConsumerToConnectedList(this);
		
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		log.debug("Disconnection of a Pull Consumer (idComponent {}) from {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.removeProxyForPullConsumerFromConnectedList(this);
		
	}

	@Override
	public Event pull(BooleanHolder hasEvent) throws ChannelNotFoundException,
			NotConnectedException {
		log.debug("Pull");
		Event e;
		hasEvent=new BooleanHolder(false);
		e = channelCtr.getEvent(this);
		if (e!=null){
			hasEvent.value=true;
		}
		return e;
	}
}