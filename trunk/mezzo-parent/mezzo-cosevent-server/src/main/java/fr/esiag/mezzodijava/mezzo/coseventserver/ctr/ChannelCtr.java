/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.nio.channels.AlreadyConnectedException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

/**
 * @author Franck
 * 
 */
public class ChannelCtr {

	// lien vers le model
	Channel channel;

	//Edit MTA : Edit Set to Vector => Utilisation du Vector parcequ'il est synchronized. 
	Vector<CallbackConsumer> callbackConsumers = new Vector<CallbackConsumer>();

	Vector<ProxyForPushConsumerOperations> proxyForPushConsumers = new Vector<ProxyForPushConsumerOperations>();

	Vector<ProxyForPushSupplierOperations> proxyForPushSuppliers = new Vector<ProxyForPushSupplierOperations>();

	/**
	 * Build instance of a ChannelCtr associated with a Channel entity
	 * 
	 * @param channel
	 *            Channel entity
	 */
	public ChannelCtr(Channel channel) {
		this.channel = channel;
	}

	public void addCallbackConsumer(CallbackConsumer callbackConsummer)
			throws AlreadyRegisteredException {
		if (!callbackConsumers.add(callbackConsummer)) {
			throw new AlreadyRegisteredException();
		}
	}

	public void removeCallbackConsumer(CallbackConsumer callbackConsummer)
			throws NotRegisteredException {
		if (!callbackConsumers.remove(callbackConsummer)) {
			throw new NotRegisteredException();
		}
	}

	public void addProxyForPushConsumer(
			ProxyForPushConsumerOperations proxyConsumer) {
		if (!proxyForPushConsumers.add(proxyConsumer)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushConsumer(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotConnectedException {
		if (!proxyForPushConsumers.remove(proxyConsumer)) {
			throw new NotConnectedException();
		}
	}

	public void addProxyForPushSupplier(
			ProxyForPushSupplierOperations proxySupplier) {
		if (!proxyForPushSuppliers.add(proxySupplier)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushSupplier(
			ProxyForPushSupplierOperations proxySupplier)
			throws NotConnectedException {
		if (!proxyForPushSuppliers.remove(proxySupplier)) {
			throw new NotConnectedException();
		}
	}

	public void addEvent(Event e) {
		channel.addEvents(e);
	}

}
