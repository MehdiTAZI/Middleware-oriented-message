package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;

/**
 * Classe ProxyForPushConsumerImpl
 * 
 * Proxy for subscribing and receiving Events, acts as a Supplier accessible to
 * a client, implementation of the ProxyForPushConsumer IDL Interface
 * 
 * UC n°: US15 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */

public class ProxyForPushConsumerImpl extends AbstractProxyImpl implements
		MessageListener, ProxyForPushConsumerOperations {

	private static Logger log = LoggerFactory
			.getLogger(ProxyForPushConsumerImpl.class);

	/**
	 * The Callback Consumer Interface to the consumer.
	 */
	private CallbackConsumer callbackConsumer;

	public ProxyForPushConsumerImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	/**
	 * Connect this consumer the the channel.
	 * 
	 * The consumer must be registerd first. It will be able to push events.
	 * 
	 * @throws NotRegisteredException
	 *             If The consumer is not registered.
	 * @throws AlreadyConnectedException
	 *             If already present in the list.
	 * @throws MaximalConnectionReachedException
	 *             If Channel Connection Capaciy is reached.
	 */
	@Override
	public void connect(CallbackConsumer c) throws AlreadyConnectedException,
			NotRegisteredException, MaximalConnectionReachedException {
		this.callbackConsumer = c;
		channelCtr.addProxyForPushConsumerToConnectedList(this);
		log.debug("Connection of a Push Consumer (idComponent {}) from {}",
				idComponent, channelCtr.getChannel().getTopic());

	}

	/**
	 * Disconnect this Consumer from the channel.
	 * 
	 * the consumer will not be able to recevie events.
	 * 
	 * @throws NotRegisteredException
	 *             If The consumer is not registered.
	 * @throws NotConnectedException
	 *             If The consumer was not connected.
	 */
	@Override
	public void disconnect() throws NotConnectedException,
			NotRegisteredException {
		channelCtr.removeProxyForPushConsumerFromConnectedList(this);
		log.debug("Disconnection of a Push Consumer (idComponent {}) from {}",
				idComponent, channelCtr.getChannel().getTopic());

	}

	/**
	 * To allow consumer receive Events from the Channel.
	 * 
	 * Call the callback interface of the consumer client to PUSH the Event.
	 * 
	 * @throws ConsumerNotFoundException
	 *             If The consumer is not reachable.
	 */
	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
		try {
			callbackConsumer.receive(evt);
			log.info("PushConsumer {} just receive an event",idComponent);

		} catch (org.omg.CORBA.SystemException ex) {
			log.error("Consumer not found", ex);
			throw new ConsumerNotFoundException(ex.getClass().getName() + ":"
					+ ex.getMessage());
		}
	}

	/**
	 * Subscribe this consumer to the channel.
	 * 
	 * The channel will store events for this consumer til it can push to it.
	 * 
	 * @throws AlreadyRegisteredException
	 *             If already present in the list.
	 */
	@Override
	public void subscribe() throws AlreadyRegisteredException {
		channelCtr.addProxyForPushConsumerToSubscribedList(this);
		log.debug("Subscribe of a Push Consumer (idComponent {}) to {}",idComponent,channelCtr.getChannel().getTopic());

		
	}

	/**
	 * Unsubscribe this consumer from the channel.
	 * 
	 * The channel will store no more events for this consumer.
	 * 
	 * @throws NotRegisteredException
	 *             If The consumer is not registered.
	 */
	@Override
	public void unsubscribe() throws NotRegisteredException {
		channelCtr.removeProxyForPushConsumerFromSubscribedList(this);
		log.debug("Unsubscribe of a Push Consumer (idComponent {}) from {}",idComponent,channelCtr.getChannel().getTopic());

	}

}
