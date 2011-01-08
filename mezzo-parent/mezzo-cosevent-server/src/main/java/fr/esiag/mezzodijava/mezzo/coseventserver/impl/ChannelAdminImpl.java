package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;

/**
 * @author fab
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
