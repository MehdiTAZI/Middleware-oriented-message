/**
 *
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

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
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.servercommons.ThreadPool;

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
    private ThreadPool threadPool;

    /**
     * 
     * @return the pool of thread.
     */
    public ThreadPool getThreadPool() {
	return threadPool;
    }

    /**
     * Build instance of a ChannelCtr associated with a Channel entity fetched
     * by this topic.
     * 
     * @param topic
     *            Channel name
     */
    public ChannelCtr(String topic) {
	log.trace("Creation of ChannelCtr for {}", topic);
	this.channel = EventServer.getInstance().createChannelEntity(
		topic,
		Integer.valueOf(CosEventServer.properties
			.getProperty("eventserver.maxchannelconnection")));
	threadPool = new ThreadPool(channel.getConnectionCapacity());
	log.debug("ChannelCtr for {} created", topic);
    }

    /**
     * Add an event to all Subscribed Push and Pull Consumers Queue of this
     * Channel.
     * 
     * @param e
     *            an Event
     */
    public void addEvent(Event e) {
	log.info("Add Event to the channel {}",channel.getTopic());
	if (CosEventServer.getDelta() + new Date().getTime() < e.header.creationdate
		+ e.header.timetolive) {

	    EventModel em = new EventConvertor().transformToEventModel(e);

	    // ajout dans la liste nécessaire pour les consumer PULL
	    // channel.getEvents().add(em);
	    // persistance de l'event
	    DAOFactory.getJdbcDAO().insertEvent(channel.getId(), em);

	    // ajout dans les listes des consumers PUSH
	    for (ConsumerModel consumer : channel.getConsumers().values()) {
		consumer.getEvents().add(em);
		// persistance de l'event
		DAOFactory.getJdbcDAO().addEventToConsumer(em.getId(),
			consumer.getId());
		log.debug("Event PUSH " + e.toString() + " " + e.body.content
			+ " nb evt =" + consumer.getEvents().size());
	    }
	    // ajout dans les listes des consumers PULL
	    for (ConsumerModel consumer : channel.getConsumersPull().values()) {
		consumer.getEvents().add(em);
		// persistance de l'event
		DAOFactory.getJdbcDAO().addEventToConsumer(em.getId(),
			consumer.getId());
		log.debug("Event PULL " + e.toString() + " " + e.body.content
			+ " nb evt =" + consumer.getEvents().size());
	    }
	    log.debug("Event ajoute a la liste");
	}

    }

    /**
     * Add a Proxy for PUSH Consumer to the Connected Consumers List for this
     * Channel.
     * 
     * The consumer must be registered first. The consumer will be able to
     * receive events.
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
	log.info("Add proxyPushConsumer {} to connected list",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel consumer = channel.getConsumers().get(idConsumer);
	if (consumer == null) {
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
	threadPool.runTask(new ThreadPushConsumer(proxyConsumer));
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
	String idConsumer = null;
	if (proxyConsumer != null){
	    idConsumer = proxyConsumer.getIdComponent();
	}
	if (channel.getConsumers().containsKey(idConsumer)
		|| proxyConsumer == null || idConsumer == null) {
	    log.error("AlreadyRegistered");
	    throw new AlreadyRegisteredException();
	} else {
	    log.info("proxyPushConsumer {} subscribed to {}",
		    proxyConsumer.toString(), channel.getTopic());
	    ConsumerModel c = channel.addSubscribedConsumer(idConsumer);
	    // persistance du Consumer
	    DAOFactory.getJdbcDAO().insertConsumer(c);
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
	log.info("Add proxyPushSupplier {} to connectedList",
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
     * Add a proxy for PULL Consumer to connected suppliers list for this
     * channel.
     * 
     * The consumer will be able to ask for new events to the channel.
     * 
     * @param proxyConsumer
     *            Proxy for pull consumer
     * @throws AlreadyConnectedException
     *             if already present in the list.
     * @throws MaximalConnectionReachedException
     *             if channel connection capacity is reached.
     */
    public void addProxyForPullConsumerToConnectedList(
	    ProxyForPullConsumerImpl proxyConsumer)
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	log.info("Add proxyPullConsumer {} to connectedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	if (channel.isConsumersConnectedListcapacityReached()) {
	    log.error("Maximal connection reached: {}",
		    channel.getConnectionCapacity());
	    throw new MaximalConnectionReachedException();
	}
	if (channel.getConsumersPull().containsKey(idConsumer)) {
	    log.error("AlreadyConnected");
	    throw new AlreadyConnectedException();
	}
	log.info("proxyPullConsumer {} connected to {}",
		proxyConsumer.toString(), channel.getTopic());
	ConsumerModel c = channel.addPullConsumer(idConsumer);
	// persistance du Consumer Pull
	DAOFactory.getJdbcDAO().insertConsumer(c);
    }

    /**
     * Add a Proxy for PULL Supplier to the Connected Suppliers List for this
     * Channel.
     * 
     * The supplier will be able to be pulled by the channel.
     * 
     * @param proxySupplier
     *            Proxy For Pull Supplier
     * @throws AlreadyConnectedException
     *             If already present in the list.
     * @throws MaximalConnectionReachedException
     *             If Channel Connection Capaciy is reached.
     */

    public void addProxyForPullSupplierToConnectedList(
	    ProxyForPullSupplierImpl proxySupplier)
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	log.info("Add proxyPullSupplier {} to connectedList",
		proxySupplier.toString());
	String idSupplier = proxySupplier.getIdComponent();
	if (channel.isSuppliersConnectedsListcapacityReached()) {
	    log.error("Maximal connection reached: {}",
		    channel.getConnectionCapacity());
	    throw new MaximalConnectionReachedException();
	}
	if (channel.getSuppliersPullConnected().containsKey(idSupplier)) {
	    log.error("Already connected");
	    throw new AlreadyConnectedException();
	}
	channel.getSuppliersPullConnected().put(idSupplier, proxySupplier);
	threadPool.runTask(new ThreadPullEvent(proxySupplier));
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
	log.info("Remove proxyPushConsumer {} to connectedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	if (!channel.getConsumers().containsKey(idConsumer)) {
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
	log.info("Remove proxyPushConsumer {} from subscribedList",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel c = new ConsumerModel();
	c.setIdConsumer(idConsumer);
	if (channel.getConsumers().remove(idConsumer) == null) {
	    log.error("{} can't unsubscribed because it's not subscribed",
		    proxyConsumer.toString());
	    throw new NotRegisteredException();
	} else {
	    // suppression dans la base
	    DAOFactory.getJdbcDAO().deleteConsumer(c.getId());
	}
    }

    /**
     * Remove a Proxy PUSH Supplier from the connected list.
     * 
     * The supplier will not be able to push events to the channel.
     * 
     * @param proxySupplier
     *            Proxy For Push Supplier
     * @throws NotConnectedException
     *             If the supplier was not connected.
     */
    public void removeProxyForPushSupplierFromConnectedList(
	    ProxyForPushSupplierImpl proxySupplier)
	    throws NotConnectedException {
	log.info("Remove proxyPushSupplier {} from connected list.",
		proxySupplier.toString());
	String idSupplier = proxySupplier.getIdComponent();
	if (channel.getSuppliersConnected().remove(idSupplier) == null) {
	    log.error("{} can't disconnect because it's not connected",
		    proxySupplier.toString());
	    throw new NotConnectedException();
	}
	log.debug("Disconnect of a PUSH Supplier from \"" + channel.getTopic()
		+ "\".");
    }

    /**
     * Remove a Proxy PULL Consumer from the connected list.
     * 
     * The consumer will not be able to pull events.
     * 
     * @param proxyConsumer
     *            Proxy For Pull Consumer
     * @throws NotConnectedException
     *             If The consumer was not connected.
     */
    public void removeProxyForPullConsumerFromConnectedList(
	    ProxyForPullConsumerImpl proxyConsumer)
	    throws NotConnectedException {
	log.info("Remove proxyPullConsumer {} from connected List",
		proxyConsumer.toString());
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel c = new ConsumerModel();
	c.setIdConsumer(idConsumer);
	if (channel.getConsumersPull().remove(idConsumer) == null) {
	    log.error("{} can't unconnect because it's not connected",
		    proxyConsumer.toString());
	    throw new NotConnectedException();
	}
	// suppression dans la base
	DAOFactory.getJdbcDAO().deleteConsumer(c.getId());
	log.debug("Disconnect of a PULL Consumer from \"" + channel.getTopic()
		+ "\".");
    }

    /**
     * Remove a Proxy PULL Supplier from the connected list.
     * 
     * The supplier will not be able to send events to the channel.
     * 
     * @param proxySupplier
     *            Proxy For Pull Supplier
     * @throws NotConnectedException
     *             If The supplier was not connected.
     */
    public void removeProxyForPullSupplierFromConnectedList(
	    ProxyForPullSupplierImpl proxySupplier)
	    throws NotConnectedException {
	log.info("Remove proxyPullSupplier {} from connected list.",
		proxySupplier.toString());
	String idSupplier = proxySupplier.getIdComponent();
	if (channel.getSuppliersPullConnected().remove(idSupplier) == null) {
	    log.error("{} can't disconnect because it's not connected",
		    proxySupplier.toString());
	    throw new NotConnectedException();
	}
	log.debug("Disconnect of a PULL Supplier from \"" + channel.getTopic()
		+ "\".");
    }

    /**
     * Remove all proxies for push Consumers from the subscribed list.
     * 
     * No further events will be stored for it.
     * 
     */
    public void removeAllProxiesForPushConsumerFromSubscribedList() {
	log.info("Remove all proxies for push Consumers from the subscribed list.");
	channel.setConsumers(Collections
		.synchronizedMap(new HashMap<String, ConsumerModel>()));
	// suppression de tous les consumers abonnés
	DAOFactory.getJdbcDAO().deleteAllConsumers(channel.getId());
    }

    /**
     * Remove all proxies push for Consumer from the connected list.
     * 
     * the consumers will not be able to receive events
     * 
     */
    public void removeAllProxiesForPushConsumerFromConnectedList() {
	log.info("Remove all proxies push for Consumer from the connected list.");
	channel.setConsumersConnected(new HashMap<String, ProxyForPushConsumerImpl>());
    }

    /**
     * Remove all proxies pull for Consumer from the connected list.
     * 
     * the consumers will not be able to receive events
     * 
     */
    public void removeAllProxiesForPullConsumerFromConnectedList() {
	log.info("Remove all proxies pull for Consumer from the connected list.");
	channel.setConsumersPull(new HashMap<String, ConsumerModel>());
    }

    /**
     * Remove all proxies push for Supplier from the connected list.
     * 
     * the suppliers will not be able to push events
     * 
     */
    public void removeAllProxiesForPushSupplierFromConnectedList() {
	log.info("Remove all proxies push for Supplier from the connected list.");
	channel.setSuppliersConnected(new HashMap<String, ProxyForPushSupplierImpl>());
    }

    /**
     * Remove all proxies pull for Supplier from the connected list.
     * 
     * the suppliers will not be able to send events
     * 
     */
    public void removeAllProxiesForPullSupplierFromConnectedList() {
	log.info("Remove all proxies pull for Supplier from the connected list.");
	channel.setSuppliersPullConnected(new HashMap<String, ProxyForPullSupplierImpl>());
    }

    /**
     * Provide the event for a proxy for pull consumer
     * 
     * @param proxyConsumer
     *            the proxy who has got those events
     * @return the event attached to the proxy consumer
     * @throws NotConnectedException
     *             if the proxy is not connected
     */
    public Event getEvent(ProxyForPullConsumerImpl proxyConsumer)
	    throws NotConnectedException {

	log.trace("GetEvents for proxyPullConsumer {}",
		proxyConsumer.toString());
	EventModel em;
	Event e;
	String idConsumer = proxyConsumer.getIdComponent();
	ConsumerModel c = channel.getConsumersPull().get(idConsumer);
	if (c == null) {
	    log.error("{} can't get events because it's not connected",
		    proxyConsumer.toString());
	    throw new NotConnectedException();
	} else {
	    em = c.getFirstFromQueue();
	    if (em != null) {
		e = new EventConvertor().transformToEvent(em);
		DAOFactory.getJdbcDAO().deleteEventByConsumer(c.getId(),
			em.getId());
	    } else {
		e = null;
	    }
	    return e;
	}
    }
}
