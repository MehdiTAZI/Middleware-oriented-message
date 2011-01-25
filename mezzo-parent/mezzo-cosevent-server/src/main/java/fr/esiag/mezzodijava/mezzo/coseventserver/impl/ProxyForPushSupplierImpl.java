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
 * Proxy for pushing Events, acts as a Consumer accessible to a client,
 * implementation of the ProxyForPushSupplier IDL Interface
 *
 * UC n°: US14 (+US children)
 *
 * @author Mezzo-Team
 *
 */

public class ProxyForPushSupplierImpl implements ProxyForPushSupplierOperations {

    private ChannelCtr channelCtr;
    private boolean connected = false;

    public ProxyForPushSupplierImpl(String topic) {
	channelCtr = BFFactory.createChannelCtr(topic);
    }

    @Override
    public void connect() throws AlreadyConnectedException,
	    MaximalConnectionReachedException {
	channelCtr.addProxyForPushSupplierToConnectedList(this);
	connected = true;
    }

    @Override
    public void disconnect() throws ChannelNotFoundException,
	    NotConnectedException {
	channelCtr.removeProxyForPushSupplierFromConnectedList(this);
	connected = false;

    }

    @Override
    public void push(Event evt) throws NotConnectedException {
	System.out.println("evt " + evt.toString());
	if (!connected) {
	    throw new NotConnectedException();
	}
	channelCtr.addEvent(evt);

    }

}
