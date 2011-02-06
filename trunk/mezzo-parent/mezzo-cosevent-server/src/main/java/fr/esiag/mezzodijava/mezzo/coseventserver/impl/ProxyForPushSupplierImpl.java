package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe ProxyForPushSupplierImpl
 *
 * Proxy for pushing Events, acts as a Consumer accessible to a client,
 * implementation of the ProxyForPushSupplier IDL Interface
 *
 * UC n°: US14 (+US children)
 *
 * @author Mezzo-Team
 *
 */

public class ProxyForPushSupplierImpl implements ProxyForPushSupplierOperations {

    /**
     * The Channel Controller used by this facade
     */
    private ChannelCtr channelCtr;

    private boolean connected = false;

    /**
     * Build a ProxyForPushSupplier instance associated with the given topic and
     * build the underlying Channel Controller.
     *
     * @param topic
     *            Channel Topic.
     */
    public ProxyForPushSupplierImpl(String topic) {
	channelCtr = BFFactory.createChannelCtr(topic);
    }

    /**
     * Connect this supplier to the the channel.
     *
     * It will be able to push events.
     *
     * @throws AlreadyConnectedException
     *             If already present in the list.
     * @throws MaximalConnectionReachedException
     *             If Channel Connection Capaciy is reached.
     */
    @Override
    public void connect() throws AlreadyConnectedException,
	    MaximalConnectionReachedException {
	channelCtr.addProxyForPushSupplierToConnectedList(this);
	connected = true;
    }

    /**
     * Disconnect this Supplier from the channel.
     *
     * It will no more be able to push events.
     *
     * @throws NotConnectedException
     *             If The Supplier was not connected.
     */
    @Override
    public void disconnect() throws NotConnectedException {
	channelCtr.removeProxyForPushSupplierFromConnectedList(this);
	connected = false;

    }

    /**
     * To allow supplier to push Events to the Channel.
     *
     * @param evt
     *            Event to PUSH.
     * @throws NotConnectedException
     *             If The Supplier was not connected.
     */
    @Override
    public void push(Event evt) throws NotConnectedException {
	if (!connected) {
	    throw new NotConnectedException();
	}
	channelCtr.addEvent(evt);

    }

}
