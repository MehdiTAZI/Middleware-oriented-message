package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;

/**
 * Classe ChannelAdminImpl
 * 
 * For Proxy access to a client, implementation of the ChannelAdmin Interface
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public class ChannelAdminImpl implements ChannelAdminOperations {
	
	private ChannelAdminCtr chanelAdminctrl;

	public ChannelAdminImpl(ChannelAdminCtr cac) {
		this.chanelAdminctrl=cac;
	}
	
	@Override
	public ProxyForPushSupplier getProxyForPushSupplier()
			throws ChannelNotFoundException {
		return chanelAdminctrl.createProxyForPushSupplier();
	}
	
	@Override
	public ProxyForPushConsumer getProxyForPushConsumer()
			throws ChannelNotFoundException {
		return chanelAdminctrl.createProxyForPushConsumer();
	}

}
