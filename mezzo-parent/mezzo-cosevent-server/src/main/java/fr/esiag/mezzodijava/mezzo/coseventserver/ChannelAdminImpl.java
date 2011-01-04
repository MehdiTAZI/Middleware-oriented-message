package fr.esiag.mezzodijava.mezzo.coseventserver;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;

/**
 * @author fab
 * 
 */
public class ChannelAdminImpl implements ChannelAdminOperations {
	
	private ChannelAdminCtr chadmctr;

	public ChannelAdminImpl(ChannelAdminCtr cac) {
		this.chadmctr=cac;
	}

	public ProxyForPushSupplier getProxyForPushSupplier()
			throws ChannelNotFoundException {
		return chadmctr.createProxyForPushSupplier();
	}

	public ProxyForPushConsumer getProxyForPushConsumer()
			throws ChannelNotFoundException {
		return null;
	}

}
