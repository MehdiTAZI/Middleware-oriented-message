package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class AbstractProxyImpl {
	
	protected String idComponent;
	/**
	 * The Channel Controller used by this facade
	 */
	protected ChannelCtr channelCtr;

	protected boolean connected = false;

	/**
	 * Build a ProxyForPushSupplier instance associated with the given topic and
	 * build the underlying Channel Controller.
	 * 
	 * @param topic
	 *            Channel Topic.
	 */
	public AbstractProxyImpl(String topic) {
		channelCtr = BFFactory.createChannelCtr(topic);
	}

}
