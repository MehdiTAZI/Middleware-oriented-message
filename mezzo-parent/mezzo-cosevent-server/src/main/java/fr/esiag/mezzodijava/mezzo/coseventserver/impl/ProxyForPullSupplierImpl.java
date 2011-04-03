package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

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

	public ProxyForPullSupplierImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	@Override
	public void connect(CallbackSupplier cs) throws ChannelNotFoundException,
			NotRegisteredException, MaximalConnectionReachedException,
			AlreadyConnectedException {
		channelCtr.addProxyForPullSupplierToConnectedList(this);
		connected = true;
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotRegisteredException, NotConnectedException {
		channelCtr.removeProxyForPullSupplierFromConnectedList(this);
		connected = false;
	}

}
