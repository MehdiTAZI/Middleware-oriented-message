package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOA;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;

public class ProxyForPushConsumerImpl extends ProxyForPushConsumerPOA implements MessageListener{

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

	@Override
	public void subscribe(CallbackConsumer cc)
			throws AlreadyRegisteredException {
		this.callbackConsumer = cc;
		channelCtr.addCallbackConsumer(cc);
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

	@Override
	public void receive(Event evt) {
			
	}

}
