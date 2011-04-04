package fr.esiag.mezzodijava.mezzo.coseventserver.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.publisher.ChannelPublisher;

/**
 * BFFactory : allow to get instances of Channel, ChannelCtr,ChannelAdminImpl
 * and ChannelAdminCtr identified by the channel topic.
 * 
 * UC n°: US14,US15 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */
public class BFFactory {

    // private static Map<String, Channel> mapChannel = new HashMap<String,
    // Channel>();

	private static Logger log = LoggerFactory.getLogger(BFFactory.class);
    private static Map<String, ChannelAdminCtr> mapChannelAdminCtr = new HashMap<String, ChannelAdminCtr>();
    private static Map<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();
    private static Map<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();

    // ----------
    private static Map<String, EventServerChannelAdminCtr> mapEventServerChannelAdminCtr = new HashMap<String, EventServerChannelAdminCtr>();
    // private static Map<Long, Channel> mapChannelId = new HashMap<Long,
    // Channel>();

    // ----------

    private static ORB orb;

    /**
     * Create and publish a ChannelImpl and associated ChannelCtr and Channel
     * entity from the channel topic.
     * 
     * @param topic
     *            Channel topic
     * @param capacity
     *            Maximal connection allowed
     * @return private unique id of the channel.
     */
    public static synchronized Channel createChannel(String topic, int capacity)
	    throws ChannelAlreadyExistsException {
	EventServer es = EventServer.getInstance();
	Channel channel = null;
	// Error if Channel Already Exists
	if (es.getMapChannel().get(topic) != null) {
		log.error("Channel already exists");
	    throw new ChannelAlreadyExistsException();
	}
	channel = es.createChannelEntity(topic, capacity);
	ChannelAdminImpl cai = createChannelAdminImpl(topic);
	// Publish the ChannelAdminImpl with Corba
	ChannelPublisher.publish(cai);
	log.info("Channel {} created with a capacity of {}",topic,capacity);
	return channel;
    }

    /**
     * Change the Channel Model connection capacity.
     * 
     * @param channel
     *            Channel entity
     * @param capacity
     *            new capacity.
     */
    public static void changeChannelCapacity(Channel channel, int capacity) {
	channel.setCapacity(capacity);
	mapChannelCtr.get(channel.getTopic()).getThreadPool().setMaxSize(capacity);
	log.debug("Capacity of channel {} changed to {}",channel,capacity);
	EventServer.getInstance().getMapChannel()
		.put(channel.getTopic(), channel);
    }

    /**
     * Create a Channel Admin Controller associated with the given topic.
     * 
     * @param topic
     *            Channel topic
     * @return existing or new ChannelAdminCtr for this topic.
     */
    public static synchronized ChannelAdminCtr createChannelAdminCtr(
	    String topic) {
	if (mapChannelAdminCtr.get(topic) == null) {
		log.info("Creation of channelAdminCtr for {}",topic);
	    mapChannelAdminCtr.put(topic, new ChannelAdminCtr(topic));
	}
	log.debug("Access to ChannelAdminCtr of {}",topic);
	return mapChannelAdminCtr.get(topic);

    }

    /**
     * Create a Channel Admin Implementation associated with the given topic.
     * 
     * @param topic
     *            Channel topic
     * @return existing or new ChannelAdminImpl for this topic.
     */
    public static synchronized ChannelAdminImpl createChannelAdminImpl(
	    String topic) {
	if (mapChannelAdminImpl.get(topic) == null) {
		log.info("Creation of channelAdminImpl for {}",topic);
	    mapChannelAdminImpl.put(topic, new ChannelAdminImpl(topic));
	}
	log.debug("Access to ChannelAdminImpl of {}",topic);
	return mapChannelAdminImpl.get(topic);
    }

    /**
     * Create a Channel Controller associated with the given topic.
     * 
     * @param topic
     *            Channel topic
     * @return existing or new ChannelCtr for this topic.
     */
    public static synchronized ChannelCtr createChannelCtr(String topic) {
	if (mapChannelCtr.get(topic) == null) {
		log.info("Creation of channelCtr for {}",topic);
	    mapChannelCtr.put(topic, new ChannelCtr(topic));
	}
	log.debug("Access to ChannelCtr of {}",topic);
	return mapChannelCtr.get(topic);
    }

    // --------------------------------EventServerChannelAdmin-----------------------------------------

    /**
     * Create a Event Server Channel Admin Controller associated with the given
     * topic.
     * 
     * @param seventServerName
     *            name of the COS event server
     * @return existing or new EventServerChannelAdminCtr for this name.
     */
    public static synchronized EventServerChannelAdminCtr createEventServerChannelAdminCtr(
	    String eventServerName) {
	if (mapEventServerChannelAdminCtr.get(eventServerName) == null) {
		log.info("Creation of EventServerChannelAdminCtr for {}",eventServerName);
	    mapEventServerChannelAdminCtr.put(eventServerName,
		    new EventServerChannelAdminCtr(eventServerName));
	}
	log.debug("Access to EventServerChannelAdminCtr of {}",eventServerName);
	return mapEventServerChannelAdminCtr.get(eventServerName);

    }

    // ----------------------------------------------------------------------------------------------

    /**
     * Create and return the singleton instance of the ORB.
     * 
     * @param args
     *            command lines argument to the ORB
     * @param props
     *            Properties structure to the ORB.
     * @return singleton instance of the ORB.
     */
    public static synchronized ORB createOrb(String[] args, Properties props) {
	if (orb == null) {
		log.info("ORB initialization");
	    orb = ORB.init(args, props);
	}
	log.debug("Access to the ORB");
	return orb;

    }

    /**
     * Return the singleton instance of the ORB.
     * 
     * @return singleton instance of the ORB.
     */
    public static synchronized ORB getOrb() {
    	log.debug("Access to the ORB");
	return orb;

    }

    /**
     * Return current ChannelCtr associated with this topic or
     * <code>null</null> if topic not exists.
     * 
     * @param topic
     *            The Topic of the wanted channel.
     * @return ChannelCtr with the specified topic or <code>null</null>
     */
    public static ChannelCtr getChannelctr(String topic) {
    	log.debug("Access to the ChannelCtr of {}", topic);
	return mapChannelCtr.get(topic);
    }

    /**
     * Return current ChannelAdmin associated with this unique id or
     * <code>null</null> if id not exists.
     * 
     * @param uniqueServerChannelId
     *            The id of the wanted channel.
     * @return ChannelAdmin with the specified id or <code>null</null>
     */
    public static ChannelAdmin getChannelAdmin(long uniqueServerChannelId) {
	Channel channelEntity = EventServer.getInstance().getChannel(
		uniqueServerChannelId);
	if (channelEntity != null) {
		log.debug("Access to ChannelAdmin of {}", uniqueServerChannelId);
	    ChannelAdminPOATie tie = new ChannelAdminPOATie(
		    mapChannelAdminImpl.get(channelEntity.getTopic()));
	    return tie._this(orb);
	}
	log.warn(" ChannelAdmin of {} does not exist", uniqueServerChannelId);
	return null;
    }

    /**
     * Test purpose only. Enable to inject a mock object in the
     * ChannelAdminCtrFactory
     * 
     * @param topic
     * @param alternateChannelAdminCtr
     *            An alternative implementation of ChannelAdminCtr typically a
     *            mock
     */
    public static void setAlternateChannelAdminCtr(String topic,
	    ChannelAdminCtr alternateChannelAdminCtr) {
    	log.debug("Set a new ChannelAdminCtr for {}",topic);
	mapChannelAdminCtr.put(topic, alternateChannelAdminCtr);
    }

    /**
     * Test purpose only. Enable to inject a mock object in the
     * ChannelCtrFactory
     * 
     * @param topic
     * @param alternateChannelCtr
     *            An alternative implementation of ChannelCtr typically a mock
     */
    public static void setAlternateChannelCtr(String topic,
	    ChannelCtr alternateChannelCtr) {
    	log.debug("Set a new ChannelCtr for {}",topic);
	mapChannelCtr.put(topic, alternateChannelCtr);
    }

    /**
     * Test purpose only. Enable to inject a mock object in the ChannelFactory.
     * 
     * @param topic
     * @param alternateChannel
     *            An alternative implementation of ServerChannelCtr typically a
     *            mock
     */
    public static synchronized void setAlternateServerChannelAdminCtr(
	    String servername, EventServerChannelAdminCtr alternateCtr) {
    	log.debug("Set a new ServerChannelAdminCtr for {}",servername);
	mapEventServerChannelAdminCtr.put(servername, alternateCtr);
    }

    /**
     * Destroy Channel, ChannelAdminCtr, ChannelAdminImpl, ChannelCtr, ChannelId
     * associated with this unique id
     * 
     * @param uniqueServerChannelId
     *            The id of the wanted channel to destroy.
     */
    public static void destroy(long idChannel) {
    	log.debug("Destruction of {}",idChannel);
	EventServer es = EventServer.getInstance();
	String channelName = es.getChannel(idChannel).getTopic();
	log.trace("Destruction of {} channel",idChannel);
	es.getMapChannel().remove(channelName);
	log.trace("Destruction of {} channelAdminCtr",idChannel);
	mapChannelAdminCtr.remove(channelName);
	log.trace("Destruction of {} channelAdminImpl",idChannel);
	mapChannelAdminImpl.remove(channelName);
	log.trace("Destruction of {} channelCtr",idChannel);
	mapChannelCtr.remove(channelName);
	log.trace("Destruction of {} channelid",idChannel);
	es.getMapChannelId().remove(idChannel);
	log.trace("Destruction of {} done",idChannel);
    }

    /**
     * Hiding public constructor.
     */
    private BFFactory() {
	super();
    }

}
