/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.nio.channels.AlreadyConnectedException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

	public Channel getChannelModel() {
		return channel;
	}

	public void setChannelModel(Channel channelModel) {
		this.channel = channelModel;
	}

	// Edit FGI : Vector -> Set synchronized car moderne
	Set<CallbackConsumer> callbackConsumers = Collections
			.synchronizedSet(new HashSet<CallbackConsumer>());

	Set<ProxyForPushConsumerOperations> proxyForPushConsumers = Collections
			.synchronizedSet(new HashSet<ProxyForPushConsumerOperations>());

	Set<ProxyForPushSupplierOperations> proxyForPushSuppliers = Collections
			.synchronizedSet(new HashSet<ProxyForPushSupplierOperations>());

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
