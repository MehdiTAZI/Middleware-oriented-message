package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOA;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;

public class ProxyForPushConsumerImpl implements MessageListener,ProxyForPushConsumerOperations{

	/**
	 * The Channel Controller used by this facade
	 */
	ChannelCtr channelCtr;

	CallbackConsumer callbackConsumer;

	/**
	 * Build an instance of ProxyForPushConsumerImpl associated to a Channel
	 * Controler
	 * 
	 * @param channelCtr A Channel Controller
	 *            
	 */
	public ProxyForPushConsumerImpl(ChannelCtr channelCtr) {
		this.channelCtr = channelCtr;
	}

	public ProxyForPushConsumerImpl() {
		//NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));		
		//this.channelCtr=ChannelHelper.narrow(nc.resolve_str(topic));
	}
	@Override
	public void subscribe(CallbackConsumer cc)
			throws AlreadyRegisteredException {
		this.callbackConsumer = cc;
		channelCtr.addCallbackConsumer(cc);
		System.out.println("Consumer Subscribed to " + channelCtr.getChannelModel().getTopic());
		
	}

	@Override
	public void unsubscribe() throws NotRegisteredException {
		channelCtr.removeCallbackConsumer(this.callbackConsumer);
	}

	@Override
	public void connect() throws AlreadyConnectedException {
		channelCtr.addProxyForPushConsumer(this);
	}

	@Override
	public void disconnect() throws NotConnectedException {
		channelCtr.removeProxyForPushConsumer(this);

	}

	//pour recevoir la notification qu'il y a des events envoyé par un supplier 
	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
			callbackConsumer.receive(evt);
	}

}
