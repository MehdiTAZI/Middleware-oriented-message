/**
 *
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
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

public class ChannelCtr implements java.nio.channels.Channel {

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
		this.channel = BFFactory.crap(topic, 0);
	}

	/**
	 * Add an event to all Subscribed Consumers to this Channel.
	 *
	 * @param e
	 *            an Event
	 */
	public void addEvent(Event e) {
		
		channel.getQueueEvents().add(e);
		
		for (ProxyForPushConsumerImpl consumer : channel
				.getConsumersSubscribed().keySet()) {
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
		
		if(channel.getQueueEvents().size() > 8){
			while(channel.getQueueEvents().size() > 0)
			       System.out.println("QUEUE PRIORITY --> " + channel.getQueueEvents().remove());
		}

		       
						
		

	}

	/**
	 * Add a Proxy for PUSH Consumer to the Connected Consumers List for this
	 * Channel.
	 *
	 * The consumer must be registered first. The consumer will be able to
	 * recevie events.
	 *
	 * @param proxyConsumer
	 *            Proxy For Push Consumer
	 * @throws NotRegisteredException
	 *             If The consumer is not registered.
	 * @throws AlreadyConnectedException
	 *             If already present in the list.
	 * @throws MaximalConnectionReachedException
	 *             If Channel Connection Capaciy is reached.
	 */
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

	/**
	 * Add a Proxy for PUSH Consumer to the Subscribed Consumers List for this
	 * Channel.
	 *
	 * The subscrition allow consumer to store event while it is not connected
	 * and receeive them when connected.
	 *
	 * @param proxyConsumer
	 *            Proxy For Push Consumer
	 * @throws AlreadyRegisteredException
	 *             If already present in the list.
	 */
	public void addProxyForPushConsumerToSubscribedList(
			ProxyForPushConsumerImpl proxyConsumer)
	throws AlreadyRegisteredException {
		if (channel.getConsumersSubscribed().containsKey(proxyConsumer)
				|| proxyConsumer == null) {
			throw new AlreadyRegisteredException();
		} else {
			channel.getConsumersSubscribed().put(proxyConsumer,
					Collections.synchronizedList(new ArrayList<Event>()));
		}
	}

	/**
	 * Add a Proxy for PUSH Supplier to the Connected Suppliers List for this
	 * Channel.
	 *
	 * The supplier will be able to push events to the channel.
	 *
	 * @param proxySupplier
	 *            Proxy For Push Supplier
	 * @throws AlreadyConnectedException
	 *             If already present in the list.
	 * @throws MaximalConnectionReachedException
	 *             If Channel Connection Capaciy is reached.
	 */
	
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

	/**
	 * Return the underlying Channel of this channel Controller.
	 *
	 * @return Channel intance
	 */
	
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Remove a connected Consumer from the connected list.
	 *
	 * the consumer will not be able to recevie events.
	 *
	 * @param proxyConsumer
	 *            Proxy For Push Consumer
	 * @throws NotRegisteredException
	 *             If The consumer is not registered.
	 * @throws NotConnectedException
	 *             If The consumer was not connected.
	 */
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

	/**
	 * Remove a subscribed Consumer from the subscribed list.
	 *
	 * No further events will be stored for it.
	 *
	 * @param proxyConsumer
	 *            Proxy For Push Consumer
	 * @throws NotRegisteredException
	 *             If The consumer was not registered.
	 */
	public void removeProxyForPushConsumerFromSubscribedList(
			ProxyForPushConsumerOperations proxyConsumer)
	throws NotRegisteredException {
		if (channel.getConsumersSubscribed().remove(proxyConsumer) == null) {
			throw new NotRegisteredException();
		}
	}
	
	public void removeAllProxiesForPushConsumerFromSubscribedList(){
		channel.setConsumersSubscribed(Collections
			    .synchronizedMap(new HashMap<ProxyForPushConsumerImpl, List<Event>>()));
	}

	public void removeAllProxiesForPushConsumerFromConnectedList(){
		channel.setConsumersConnected(new HashSet<ProxyForPushConsumerImpl>());
	}
	public void removeAllProxiesForPushSupplierFromConnectedList(){
		channel.setSuppliersConnected(new HashSet<ProxyForPushSupplierImpl>());
	}
	/**
	 * Remove a Proxy PUSH Supplier from the connected list.
	 *
	 * The consumer will not be able to push events to the channel.
	 *
	 * @param proxySupplier
	 *            Proxy For Push Supplier
	 * @throws NotConnectedException
	 *             If The consumer was not connected.
	 */
	public void removeProxyForPushSupplierFromConnectedList(
			ProxyForPushSupplierOperations proxySupplier)
	throws NotConnectedException {
		if (!channel.getSuppliersConnected().remove(proxySupplier)) {
			throw new NotConnectedException();
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

}
