package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe ProxyForPushSupplierImpl
 * 
 * Proxy for pushing Events, acts as a Consumer 
 * accessible to a client, implementation of the ProxyForPushSupplier IDL Interface
 * 
 * UC n°: US14 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */


public class ProxyForPushSupplierImpl implements ProxyForPushSupplierOperations{

	private ChannelCtr channelCtr;
	private String channel;
	
	/**
	 * @param channelCtr A channel Controler
	 */
	@Deprecated
	public ProxyForPushSupplierImpl(ChannelCtr channelCtr){
		this.channelCtr = channelCtr;		 		
	}
	public ProxyForPushSupplierImpl(String channel){
		channelCtr = BFFactory.createChannelCtr(channel);
		this.channel = channel;		 		
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
		
		System.out.println("evt "+evt.toString());
		channelCtr.addEvent(evt);
		
	}

}
