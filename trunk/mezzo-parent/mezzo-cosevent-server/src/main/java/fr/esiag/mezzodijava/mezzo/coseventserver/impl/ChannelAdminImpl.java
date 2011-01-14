package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe ChannelAdminImpl
 * 
 * For Proxy access to a client, implementation of the ChannelAdmin IDL Interface
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public class ChannelAdminImpl implements ChannelAdminOperations {
	
	private ChannelAdminCtr channelAdminctrl;
	private String channel;

	public ChannelAdminImpl(ChannelAdminCtr cac) {
		this.channelAdminctrl=cac;
	}
	public ChannelAdminImpl(String channel) {
		this.channel=channel;
		this.channelAdminctrl= BFFactory.createChannelAdminCtr(channel);
	}
	
	@Override
	public ProxyForPushSupplier getProxyForPushSupplier()
			throws ChannelNotFoundException {
		return channelAdminctrl.createProxyForPushSupplier();
	}
	
	@Override
	public ProxyForPushConsumer getProxyForPushConsumer()
			throws ChannelNotFoundException {
		return channelAdminctrl.createProxyForPushConsumer();
	}

}
