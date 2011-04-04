package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplierOperations;

/**
 * Classe ProxyForPullSupplierImpl
 * 
 * 
 * @author Mezzo-Team
 * 
 */

public class ProxyForPullSupplierImpl extends AbstractProxyImpl implements
		ProxyForPullSupplierOperations {
	
	private static Logger log = LoggerFactory.getLogger(ProxyForPullSupplierImpl.class);


	public ProxyForPullSupplierImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	@Override
	public void connect(CallbackSupplier cs) throws ChannelNotFoundException,
			NotRegisteredException, MaximalConnectionReachedException,
			AlreadyConnectedException {
		log.debug("Connection of a Pull Supplier (idComponent {}) to {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.addProxyForPullSupplierToConnectedList(this);
		connected = true;
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotRegisteredException, NotConnectedException {
		log.debug("Disconnection of a Pull Supplier (idComponent {}) from {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.removeProxyForPullSupplierFromConnectedList(this);
		connected = false;
	}

}
