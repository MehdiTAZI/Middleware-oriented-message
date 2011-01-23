package fr.esiag.mezzodijava.mezzo.coseventserver.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class BFFactory {

	private static Map<String, Channel> mapChannel = new HashMap<String, Channel>();

	private static Map<String, ChannelAdminCtr> mapChannelAdminCtr = new HashMap<String, ChannelAdminCtr>();
	private static Map<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();
	private static Map<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();
	private static ORB orb;

	public static synchronized Channel createChannel(String topic, int capacity) {
		if (mapChannel.get(topic) == null) {
			mapChannel.put(topic, new Channel(topic, capacity));
		}
		return mapChannel.get(topic);
	}

	public static synchronized ChannelAdminCtr createChannelAdminCtr(
			String topic) {
		if (mapChannelAdminCtr.get(topic) == null) {
			mapChannelAdminCtr.put(topic, new ChannelAdminCtr(topic));
		}
		return mapChannelAdminCtr.get(topic);

	}

	public static synchronized ChannelAdminImpl createChannelAdminImpl(
			String topic) {
		if (mapChannelAdminImpl.get(topic) == null) {
			mapChannelAdminImpl.put(topic, new ChannelAdminImpl(topic));
		}
		return mapChannelAdminImpl.get(topic);
	}

	public static synchronized ChannelCtr createChannelCtr(String topic) {
		if (mapChannelCtr.get(topic) == null) {
			mapChannelCtr.put(topic, new ChannelCtr(topic));
		}
		return mapChannelCtr.get(topic);
	}

	public static synchronized ORB createOrb(String[] args, Properties props) {
		if (orb == null) {
			orb = ORB.init(args, props);
		}
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

	public static synchronized ChannelAdminImpl initiateChannel(String topic,
			int capacity) {
		createChannel(topic, capacity);
		return createChannelAdminImpl(topic);
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

	/**
	 * Hiding public constructor.
	 */
	private BFFactory() {
		super();
	}

}
