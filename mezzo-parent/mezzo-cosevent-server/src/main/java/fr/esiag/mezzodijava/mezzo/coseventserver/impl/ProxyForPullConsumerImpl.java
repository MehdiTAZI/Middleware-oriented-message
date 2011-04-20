package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.omg.CORBA.Any;
import org.omg.CORBA.BooleanHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumerOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

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

	/**
	 * constructor
	 * @param topic topic associated to the ppcImpl
	 * @param idComponent
	 */
	public ProxyForPullConsumerImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	/**
	 * connection to the cosEventServer
	 */
	@Override
	public void connect() throws ChannelNotFoundException,
			MaximalConnectionReachedException, AlreadyConnectedException {
		log.debug("Connection of a Pull Consumer (idComponent {}) to {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.addProxyForPullConsumerToConnectedList(this);
		connected=true;
	}

	/**
	 * disconnection of the cosEventServer
	 */
	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		log.debug("Disconnection of a Pull Consumer (idComponent {}) from {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.removeProxyForPullConsumerFromConnectedList(this);
		connected=false;
		
	}

	/**
	 * use to pull a new Event to the consumer
	 */
	@Override
	public Event pull(BooleanHolder hasEvent) throws ChannelNotFoundException,
			NotConnectedException {
		log.info("Pull (idComponent {})",idComponent);
		if (!connected) {
			log.error("Not connected");
		    throw new NotConnectedException();
		}
		Event e;
		e = channelCtr.getEvent(this);
		if (e!=null){
			hasEvent.value = true;
			return e;
		}else{
			hasEvent.value = false;
			Any a = BFFactory.getOrb().create_any();
			Header h = new Header();
			Body b = new Body();
			b.content=a;
			return new Event(h,b);
		}
	}
}
