package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;

public class ProxyForPushSupplierImpl implements ProxyForPushSupplierOperations{

	private ChannelCtr channelCtr;
	/**
	 * 
	 * @param channelCtr A channel Controler
	 */
	public ProxyForPushSupplierImpl(ChannelCtr channelCtr){
		this.channelCtr = channelCtr;
	}
	@Override
	public void connect() throws ChannelNotFoundException,
			MaximalConnectionReachedException, AlreadyConnectedException {
		channelCtr.addProxyForPushSupplier(this);
		
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		channelCtr.removeProxyForPushSupplier(this);
		
	}

	@Override
	public void push(Event evt) throws ChannelNotFoundException {
		channelCtr.addEvent(evt);
		
	}

}
