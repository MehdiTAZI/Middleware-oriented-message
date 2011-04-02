package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.List;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void subscribe() throws AlreadyRegisteredException,
			ChannelNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe() throws ChannelNotFoundException,
			NotRegisteredException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(CallbackSupplier cs) throws ChannelNotFoundException,
			NotRegisteredException, MaximalConnectionReachedException,
			AlreadyConnectedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotRegisteredException, NotConnectedException {
		// TODO Auto-generated method stub
		
	}

    
}
