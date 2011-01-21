/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.ArrayList;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
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
	private Channel channel;

	/**
	 * Build instance of a ChannelCtr associated with a Channel entity fetched
	 * byhis topic.
	 * 
	 * @param topic
	 *            Channel name
	 */
	public ChannelCtr(String topic) {
		this.channel = BFFactory.createChannel(topic, 0);

	}

	public Channel getChannel() {
		return channel;
	}

	public void addProxyForPushConsumerToSubscribedList(
			ProxyForPushConsumerImpl proxyConsumer)
			throws AlreadyRegisteredException {
		if (channel.getConsumersSubscribed().containsKey(proxyConsumer)
				|| proxyConsumer == null) {
			throw new AlreadyRegisteredException();
		} else {
			channel.getConsumersSubscribed().put(proxyConsumer,
					new ArrayList<Event>());
		}
	}

	public void removeProxyForPushConsumerFromSubscribedList(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotRegisteredException {
		if (channel.getConsumersSubscribed().remove(proxyConsumer) == null) {
			throw new NotRegisteredException();
		}
	}

	public void addProxyForPushConsumerToConnectedList(
			ProxyForPushConsumerImpl proxyConsumer)
			throws NotRegisteredException, AlreadyConnectedException,
			MaximalConnectionReachedException {
		if (!channel.getConsumersSubscribed().containsKey(proxyConsumer)) {
			throw new NotRegisteredException();
		}
		if (channel.isConsumersConnectedListcapacityReached()) {
			throw new MaximalConnectionReachedException();
		}
		if (!channel.getConsumersConnected().add(proxyConsumer)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushConsumerFromConnectedList(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotRegisteredException, NotConnectedException {
		if (!channel.getConsumersSubscribed().containsKey(proxyConsumer)) {
			throw new NotRegisteredException();
		}
		if (!channel.getConsumersConnected().remove(proxyConsumer)) {
			throw new NotConnectedException();
		}
	}

	public void addProxyForPushSupplierToConnectedList(
			ProxyForPushSupplierImpl proxySupplier)
			throws AlreadyConnectedException, MaximalConnectionReachedException {
		if (channel.isSuppliersConnectedsListcapacityReached()) {
			throw new MaximalConnectionReachedException();
		}
		if (!channel.getSuppliersConnected().add(proxySupplier)) {
			throw new AlreadyConnectedException();
		}
	}

	public void removeProxyForPushSupplierFromConnectedList(
			ProxyForPushSupplierOperations proxySupplier)
			throws NotConnectedException {
		if (!channel.getSuppliersConnected().remove(proxySupplier)) {
			throw new NotConnectedException();
		}
	}

	public void addEvent(Event e) {
		for (ProxyForPushConsumerImpl consumer : channel
				.getConsumersSubscribed().keySet()) {
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
	}

}
