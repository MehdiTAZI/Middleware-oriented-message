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
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

/**
 * Classe ChannelCtr
 * 
 * To interact with the model
 * 
 * UC n°: US14,US15 (+US children) 
 * 
 * @author Mezzo-Team
 * 
 */

public class ChannelCtr {

	// lien vers le model
	Channel channel;
	
	// Edit FGI : Vector -> Set synchronized car moderne
	Set<CallbackConsumer> callbackConsumers = Collections
			.synchronizedSet(new HashSet<CallbackConsumer>());
	
	/**
	 * Build instance of a ChannelCtr associated with a Channel entity
	 * 
	 * @param channel
	 *            Channel entity
	 */
	public ChannelCtr(Channel channel) {
		this.channel = channel;
	}
	
	public Channel getChannelModel() {
		return channel;
	}

	public void setChannelModel(Channel channelModel) {
		this.channel = channelModel;
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
			ProxyForPushConsumerImpl proxyConsumer) {
		if (!channel.getProxyForPushConsumers().add(proxyConsumer)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushConsumer(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotConnectedException {
		if (!channel.getProxyForPushConsumers().remove(proxyConsumer)) {
			throw new NotConnectedException();
		}
	}

	public void addProxyForPushSupplier(
			ProxyForPushSupplierImpl proxySupplier) {
		if (!this.channel.getProxyForPushSuppliers().add(proxySupplier)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushSupplier(
			ProxyForPushSupplierOperations proxySupplier)
			throws NotConnectedException {
		if (!this.channel.getProxyForPushSuppliers().remove(proxySupplier)) {
			throw new NotConnectedException();
		}
	}

	public void addEvent(Event e) {
		channel.addEvents(e);
	}

}
