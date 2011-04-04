package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class ProxyForPushSupplierImpl extends AbstractProxyImpl implements
	ProxyForPushSupplierOperations {
	
	private static Logger log = LoggerFactory
	.getLogger(ProxyForPushSupplierImpl.class);

    public ProxyForPushSupplierImpl(String topic, String idComponent) {
	super(topic, idComponent);
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
	connected=true;
	log.debug("Connection of a Push Supplier (idComponent {}) from {}",
			idComponent, channelCtr.getChannel().getTopic());

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
	connected=false;
	log.debug("Disconnection of a Push Supplier (idComponent {}) from {}",
			idComponent, channelCtr.getChannel().getTopic());

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
		log.error("Not connected");
	    throw new NotConnectedException();
	}

	channelCtr.addEvent(evt);
	log.debug(" Push Consumer (idComponent {}) just sent an Event on {}",
			idComponent, channelCtr.getChannel().getTopic());

    }
}
