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
 * For Proxy access to a client, implementation of the ChannelAdmin IDL
 * Interface
 *
 * UC n°: US14,US15 (+US children)
 *
 * @author Mezzo-Team
 *
 */

public class ChannelAdminImpl implements ChannelAdminOperations {

    private ChannelAdminCtr channelAdminctrl;
    private String topic;

    /**
     * Constructor for a ChannelAdmin implementation. Build the underlying
     * ChannelAdminCtr.
     *
     * @param topic
     *            Channel Topic
     */
    public ChannelAdminImpl(String topic) {
	this.topic = topic;
	this.channelAdminctrl = BFFactory.createChannelAdminCtr(topic);
    }

    /**
     * Get to the client a Proxy on this Channel to access it as an PUSH
     * Consumer.
     *
     * @return a ProxyForPushConsumer implementation
     * @see fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations#getProxyForPushSupplier()
     *
     */
    @Override
    public ProxyForPushConsumer getProxyForPushConsumer()
	    throws ChannelNotFoundException {
	return channelAdminctrl.createProxyForPushConsumer();
    }

    /**
     * Get to the client a Proxy on this Channel to access it as an PUSH
     * Supplier.
     *
     * @return a ProxyForPushSupplier implementation
     * @see fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminOperations#getProxyForPushSupplier()
     *
     */
    @Override
    public ProxyForPushSupplier getProxyForPushSupplier()
	    throws ChannelNotFoundException {
	return channelAdminctrl.createProxyForPushSupplier();
    }

    /**
     * Return the topic associated with this channel admin.
     *
     * @return topic associated with the Channel Admin
     */
    public String getTopic() {
	return topic;
    }

}
