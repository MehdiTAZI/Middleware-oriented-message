package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplierOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;

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

	/**
	 * The Callback Supplier Interface to the supplier.
	 */
	private CallbackSupplier callbackSupplier;

	public ProxyForPullSupplierImpl(String topic, String idComponent) {
		super(topic, idComponent);
	}

	@Override
	public void connect(CallbackSupplier cs) throws ChannelNotFoundException,
			MaximalConnectionReachedException,
			AlreadyConnectedException {
		log.debug("Connection of a Pull Supplier (idComponent {}) to {}",idComponent,channelCtr.getChannel().getTopic());
		this.callbackSupplier = cs;
		channelCtr.addProxyForPullSupplierToConnectedList(this);
		connected=true;
	}

	@Override
	public void disconnect() throws ChannelNotFoundException,
			NotConnectedException {
		log.debug("Disconnection of a Pull Supplier (idComponent {}) from {}",idComponent,channelCtr.getChannel().getTopic());
		channelCtr.removeProxyForPullSupplierFromConnectedList(this);
		connected=false;
	}
	
	public Event ask() throws SupplierNotFoundException{
		Event e;
		try {
			e = callbackSupplier.ask();
			log.debug("PullSupplier {} just ask for an event",idComponent);
			return e;
		} catch (org.omg.CORBA.SystemException ex) {
			log.error("Supplier not found", ex);
			throw new SupplierNotFoundException(ex.getClass().getName() + ":"
					+ ex.getMessage());
		}
		
	}

}
