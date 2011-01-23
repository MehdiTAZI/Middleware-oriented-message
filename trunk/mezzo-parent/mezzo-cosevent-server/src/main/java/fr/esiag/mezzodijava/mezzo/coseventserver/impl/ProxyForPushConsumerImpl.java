package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

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

public class ProxyForPushConsumerImpl implements MessageListener,
		ProxyForPushConsumerOperations {

	/**
	 * The Channel Controller used by this facade
	 */
	ChannelCtr channelCtr;

	CallbackConsumer callbackConsumer;

	public ProxyForPushConsumerImpl(String topic) {
		this.channelCtr = BFFactory.createChannelCtr(topic);
	}

	@Override
	public void subscribe(CallbackConsumer cc)
			throws AlreadyRegisteredException {
		this.callbackConsumer = cc;
		channelCtr.addProxyForPushConsumerToSubscribedList(this);
		System.out.println("Consumer Subscribed to "
				+ channelCtr.getChannel().getTopic());

	}

	@Override
	public void unsubscribe() throws NotRegisteredException {
		channelCtr.removeProxyForPushConsumerFromSubscribedList(this);
	}

	@Override
	public void connect() throws AlreadyConnectedException,
			AlreadyConnectedException, NotRegisteredException,
			MaximalConnectionReachedException {
		channelCtr.addProxyForPushConsumerToConnectedList(this);

	}

	@Override
	public void disconnect() throws NotConnectedException,
			NotRegisteredException {
		channelCtr.removeProxyForPushConsumerFromConnectedList(this);

	}

	// pour recevoir une notification s'il y a des events envoyÃ©s par un
	// supplier
	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
		try {
			callbackConsumer.receive(evt);
		} catch (org.omg.CORBA.COMM_FAILURE ex) {
			throw new ConsumerNotFoundException(ex.toString());
		}
	}

}
