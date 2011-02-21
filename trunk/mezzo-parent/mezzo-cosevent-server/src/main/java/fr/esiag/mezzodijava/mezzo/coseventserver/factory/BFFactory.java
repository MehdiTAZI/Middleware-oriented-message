package fr.esiag.mezzodijava.mezzo.coseventserver.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.EventServerChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
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

    private static Map<String, Channel> mapChannel = new HashMap<String, Channel>();

    private static Map<String, ChannelAdminCtr> mapChannelAdminCtr = new HashMap<String, ChannelAdminCtr>();
    private static Map<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();
    private static Map<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();

    // ----------
    private static Map<String, EventServerChannelAdminCtr> mapEventServerChannelAdminCtr = new HashMap<String, EventServerChannelAdminCtr>();
    private static Map<String, EventServerChannelAdminImpl> mapEventServerChannelAdminImpl = new HashMap<String, EventServerChannelAdminImpl>();
    private static Map<Long, Channel> mapChannelId = new HashMap<Long, Channel>();

    // ----------

    private static ORB orb;

    public static void setOrb(ORB orb) {
	BFFactory.orb = orb;
    }

    /**
     * Create a Channel Entity associated with the given topic.
     * 
     * @param topic
     *            Channel topic
     * @param capacity
     *            Maximal connection allowed
     * @return existing or new Channel for this topic.
     */
    public static synchronized Channel createChannelEntity(String topic,
	    int capacity) {
	if (mapChannel.get(topic) == null) {
	    mapChannel.put(topic, new Channel(topic, capacity));
	}
	return mapChannel.get(topic);
    }

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
    public static synchronized long createChannel(String topic, int capacity)
	    throws ChannelAlreadyExistsException {
	Channel channel = null;
	// Error if Channel Allready Exists
	if (mapChannel.get(topic) != null) {
	    throw new ChannelAlreadyExistsException();
	}
	channel = createChannelEntity(topic, capacity);
	ChannelAdminImpl cai = createChannelAdminImpl(topic);
	// Publish the ChannelAdminImpl with Corba
	ChannelPublisher.publish(cai, orb);
	// Register the channel entity in id map
	mapChannelId.put(channel.getIdentifier(), channel);
	return channel.getIdentifier();
    }

    public static void changeChannelCapacity(Channel channel, int capacity) {
	channel.setCapacity(capacity);
	mapChannel.put(channel.getTopic(), channel);
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
	    mapChannelAdminCtr.put(topic, new ChannelAdminCtr(topic));
	}
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
	    mapChannelAdminImpl.put(topic, new ChannelAdminImpl(topic));
	}
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
	    mapChannelCtr.put(topic, new ChannelCtr(topic));
	}
	return mapChannelCtr.get(topic);
    }

    // --------------------------------EventServerChannelAdmin-----------------------------------------

    public static synchronized EventServerChannelAdminCtr createEventServerChannelAdminCtr(
	    String eventServerName) {
	if (mapEventServerChannelAdminCtr.get(eventServerName) == null) {
	    mapEventServerChannelAdminCtr.put(eventServerName,
		    new EventServerChannelAdminCtr(eventServerName));
	}
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
	    orb = ORB.init(args, props);
	}
	return orb;

    }
    
    /**
     * Return the singleton instance of the ORB.
     * 
     * @return singleton instance of the ORB.
     */
    public static synchronized ORB getOrb() {
	return orb;

    }

    /**
     * Return current instance of Channel Bean associated with this topic or
     * <code>null</null> if not channel exists.
     * 
     * @param topic
     *            The Topic of the wanted channel.
     * @return Channel with the specified topic or <code>null</null>
     */
    public static Channel getChannel(String topic) {
	return mapChannel.get(topic);
    }

    public static ChannelCtr getChannelctr(String topic) {
	return mapChannelCtr.get(topic);
    }

    public static Channel getChannel(long id) {
	return mapChannelId.get(id);
    }

    public static ChannelAdmin getChannelAdmin(long uniqueServerChannelId) {
	Channel channelEntity = getChannel(uniqueServerChannelId);
	if (channelEntity != null) {
	    ChannelAdminPOATie tie = new ChannelAdminPOATie(
		    mapChannelAdminImpl.get(mapChannelId.get(
			    uniqueServerChannelId).getTopic()));
	    return tie._this(orb);
	}
	return null;
    }

    /**
     * Test purpose only. Enable to inject a mock object in the ChannelFactory.
     * 
     * @param topic
     * @param alternateChannel
     *            An alternative implementation of Channel typically a mock
     */
    public static synchronized void setAlternateChannel(String topic,
	    Channel channel) {
	mapChannel.put(topic, channel);
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
	mapChannelCtr.put(topic, alternateChannelCtr);
    }

    public static void destroy(long idChannel) {
	String channelName = mapChannelId.get(idChannel).getTopic();
	mapChannel.remove(channelName);
	mapChannelAdminCtr.remove(channelName);
	mapChannelAdminImpl.remove(channelName);
	mapChannelCtr.remove(channelName);
	mapChannelId.remove(idChannel);
    }

    /**
     * Hiding public constructor.
     */
    private BFFactory() {
	super();
    }

}
