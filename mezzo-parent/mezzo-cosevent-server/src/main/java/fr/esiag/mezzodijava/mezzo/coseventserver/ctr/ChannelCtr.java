/**
 *
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.PriorityEventModelComparator;

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
    private static Logger log = LoggerFactory.getLogger(ChannelCtr.class);

    // lien vers le model
    private Channel channel;
    private Comparator<EventModel> comparator = new PriorityEventModelComparator();

    /**
     * Build instance of a ChannelCtr associated with a Channel entity fetched
     * by this topic.
     * 
     * @param topic
     *            Channel name
     */
    public ChannelCtr(String topic) {
	log.trace("Creation of ChannelCtr for {}", topic);
	this.channel = EventServer.getInstance()
		.createChannelEntity(topic, 100);
	log.info("ChannelCtr for {} created", topic);
    }

    /**
     * Add an event to all Subscribed Consumers to this Channel.
     * 
     * @param e
     *            an Event
     */
    public void addEvent(Event e) {
	log.trace("Add Event");
	if (CosEventServer.getDelta() + new Date().getTime() < e.header.creationdate
		+ e.header.timetolive) {

	    EventModel em = new EventConvertor().transformToEventModel(e);

	    // ajout dans les listes des consumers PUSH
	    for (ConsumerModel consumer : channel.getConsumers().values()) {
		// channel.getConsumersSubscribed().get(consumer).add(e);
		consumer.getEvents().add(em);
		log.info("Event PUSH " + e.toString() + " " + e.body.content
			+ " nb evt =" + consumer.getEvents().size());
	    }
	    // ajout dans la liste nécessaire pour les consummer PULL
	    channel.getEvents().add(em);
	    DAOFactory.getChannelDAO().update(channel);
	    log.debug("Event ajoute a la liste");
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
	log.trace("Add proxyPushConsumer {} to connected list",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	if (!channel.getConsumers().containsKey(idConsumer)) {
	    log.error("{} can't connect because it's not registered",
		    proxyConsumer.toString());
	    throw new NotRegisteredException();
	}
	if (channel.isConsumersConnectedListcapacityReached()) {
	    log.error(
		    "{} can't connect because maximal connection reached: {}",
		    channel.getConnectionCapacity());
	    throw new MaximalConnectionReachedException();
	}
	if (channel.getConsumersConnected().containsKey(idConsumer)) {
	    log.error("{} can't connect because it's already connected");
	    throw new AlreadyConnectedException();
	}
	channel.getConsumersConnected().put(idConsumer, proxyConsumer);
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
	log.trace("Add proxyPushConsumer {} to subscribedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel c = new ConsumerModel();
	c.setIdConsumer(idConsumer);
	if (channel.getConsumers().containsKey(idConsumer)
		|| proxyConsumer == null) {
	    log.error("AlreadyRegistered");
	    // if (channel.getConsumersSubscribed().containsKey(proxyConsumer)||
	    // proxyConsumer == null) {
	    throw new AlreadyRegisteredException();
	} else {
	    log.info("proxyPushConsumer {} subscribed to {}",
		    proxyConsumer.toString(), channel.getTopic());
	    c.setEvents(Collections
		    .synchronizedSortedSet(new TreeSet<EventModel>(comparator)));
	    channel.getConsumers().put(idConsumer, c);
	    DAOFactory.getChannelDAO().persist(channel);
	    // .put(proxyConsumer,Collections.synchronizedSortedSet(new
	    // TreeSet<Event>(comparator)));
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
	log.trace("Add proxyPushSupplier {} to connectedList",
		proxySupplier.toString());
	String idSupplier = proxySupplier.getIdComponent();
	if (channel.isSuppliersConnectedsListcapacityReached()) {
	    log.error("Maximal connection reached: {}",
		    channel.getConnectionCapacity());
	    throw new MaximalConnectionReachedException();
	}
	if (channel.getSuppliersConnected().containsKey(idSupplier)) {
	    log.error("Already connected");
	    throw new AlreadyConnectedException();
	}
	channel.getSuppliersConnected().put(idSupplier, proxySupplier);
	log.info("proxyPushSupplier {} connected to {}",
		proxySupplier.toString(), channel.getTopic());
    }

    /**
     * Return the underlying Channel of this channel Controller.
     * 
     * @return Channel intance
     */

    public Channel getChannel() {
	log.trace("access to {}", channel.getTopic());
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
	    ProxyForPushConsumerImpl proxyConsumer)
	    throws NotRegisteredException, NotConnectedException {
	log.trace("Remove proxyPushConsumer {} to connectedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	// ConsumerModel c = new ConsumerModel();
	// c.setIdConsumer(idConsumer);
	if (!channel.getConsumers().containsKey(idConsumer)) {
	    // if (!channel.getConsumersSubscribed().containsKey(proxyConsumer))
	    // {
	    log.error("{} can't disconnect because it's not subscribed",
		    proxyConsumer.toString());
	    throw new NotRegisteredException();
	}
	if (channel.getConsumersConnected().remove(idConsumer) == null) {
	    log.error("{} can't disconnect because it's not connected",
		    proxyConsumer.toString());
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
	    ProxyForPushConsumerImpl proxyConsumer)
	    throws NotRegisteredException {
	log.trace("Remove proxyPushConsumer {} from subscribedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel c = new ConsumerModel();
	c.setIdConsumer(idConsumer);
	if (channel.getConsumers().remove(idConsumer) == null) {
	    log.error("{} can't unsubscribed because it's not subscribed",
		    proxyConsumer.toString());
	    throw new NotRegisteredException();
	} else {
	    DAOFactory.getChannelDAO().persist(channel);
	}
    }

    /**
     * Remove all proxies for push Consumers from the subscribed list.
     * 
     * No further events will be stored for it.
     * 
     */
    public void removeAllProxiesForPushConsumerFromSubscribedList() {
	log.trace("Remove all proxies for push Consumers from the subscribed list.");
	channel.setConsumers(Collections
		.synchronizedMap(new HashMap<String, ConsumerModel>()));
	DAOFactory.getChannelDAO().persist(channel);
    }

    /**
     * Remove all proxies push for Consumer from the connected list.
     * 
     * the consumers will not be able to receive events
     * 
     */
    public void removeAllProxiesForPushConsumerFromConnectedList() {
	log.trace("Remove all proxies push for Consumer from the connected list.");
	channel.setConsumersConnected(new HashMap<String, ProxyForPushConsumerImpl>());
    }

    /**
     * Remove all proxies push for Supplier from the connected list.
     * 
     * the suppliers will not be able to push events
     * 
     */
    public void removeAllProxiesForPushSupplierFromConnectedList() {
	log.trace("Remove all proxies push for Supplier from the connected list.");
	channel.setSuppliersConnected(new HashMap<String, ProxyForPushSupplierImpl>());
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
	    ProxyForPushSupplierImpl proxySupplier)
	    throws NotConnectedException {
	log.trace("Remove proxyPushSupplier {} from connected list.",
		proxySupplier.toString());
	String idSupplier = proxySupplier.getIdComponent();
	if (channel.getSuppliersConnected().remove(idSupplier) == null) {
	    log.error("{} can't disconnect because it's not connected",
		    proxySupplier.toString());
	    throw new NotConnectedException();
	}
	log.debug("Disconnect of a PUSH Consumer from \"" + channel.getTopic()
		+ "\".");
    }

}
