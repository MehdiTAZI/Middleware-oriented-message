/**
 *
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.PriorityEventComparator;

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
    private Comparator<Event> comparator = new PriorityEventComparator();

    /**
     * Build instance of a ChannelCtr associated with a Channel entity fetched
     * by this topic.
     * 
     * @param topic
     *            Channel name
     */
    public ChannelCtr(String topic) {
	this.channel = BFFactory.createChannelEntity(topic, 0);
    }

    /**
     * Add an event to all Subscribed Consumers to this Channel.
     * 
     * @param e
     *            an Event
     */
    public void addEvent(Event e) {

	if (CosEventServer.getDelta() + new Date().getTime() < e.header.creationdate
		+ e.header.timetolive) {
		
		EventModel em = new EventConvertor().transformToEventModel(e);

		// ajout dans les listes des consumers PUSH
		for (ConsumerModel consumer : channel.getConsumers()) {

			System.out.println("Event PUSH " + e.body.content);
			//channel.getConsumersSubscribed().get(consumer).add(e);
			consumer.getEvents().add(em);
			System.out
			.println("Event PUSH "
				+ e.toString()
				+ " "
				+ e.body.content
				+ " nb evt ="
				+ consumer.getEvents().size());
	    }
	    // ajout dans la liste nécessaire pour les consummer PULL
	    channel.getEvents().add(em);
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
	    ProxyForPushConsumerImpl proxyConsumer, String idConsumer)
	    throws NotRegisteredException, AlreadyConnectedException,
	    MaximalConnectionReachedException {
	if (!channel.getConsumersConnected().containsValue(proxyConsumer)) {
	    throw new NotRegisteredException();
	}
	if (channel.isConsumersConnectedListcapacityReached()) {
	    throw new MaximalConnectionReachedException();
	}
	if (channel.getConsumersConnected().put(idConsumer, proxyConsumer)==null) {
	    throw new AlreadyConnectedException();
	}
    }

    /**
     * Add a Proxy for PUSH Consumer to the Subscribed Consumers List for this
     * Channel.
     * 
     * The subscribtion allow consumer to store event while it is not connected
     * and receive them when connected.
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
	    channel.getConsumersSubscribed().put(
		    proxyConsumer,
		    Collections.synchronizedSortedSet(new TreeSet<Event>(
			    comparator)));
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
	System.out.println("Connect of a PUSH Supplier to \""
		+ channel.getTopic() + "\".");
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
     * the consumer will not be able to receive events.
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

    /**
     * Remove all proxies for push Consumers from the subscribed list.
     * 
     * No further events will be stored for it.
     * 
     */
    public void removeAllProxiesForPushConsumerFromSubscribedList() {
	channel.setConsumersSubscribed(Collections
		.synchronizedMap(new HashMap<ProxyForPushConsumerImpl, SortedSet<Event>>()));
    }

    /**
     * Remove all proxies push for Consumer from the connected list.
     * 
     * the consumers will not be able to receive events
     * 
     */
    public void removeAllProxiesForPushConsumerFromConnectedList() {
	channel.setConsumersConnected(new HashSet<ProxyForPushConsumerImpl>());
    }

    /**
     * Remove all proxies push for Supplier from the connected list.
     * 
     * the suppliers will not be able to push events
     * 
     */
    public void removeAllProxiesForPushSupplierFromConnectedList() {
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
	System.out.println("Disconnect of a PUSH Consumer from \""
		+ channel.getTopic() + "\".");
    }
    
    public Event removeEvent(List<EventModel> liste, int index){
    	EventModel em = liste.remove(index);
    	Event e = new EventConvertor().transformToEvent(em);
    	return e;
    	
    }

}
