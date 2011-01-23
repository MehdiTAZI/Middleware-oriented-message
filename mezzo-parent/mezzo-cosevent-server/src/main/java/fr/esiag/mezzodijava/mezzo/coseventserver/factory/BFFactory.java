package fr.esiag.mezzodijava.mezzo.coseventserver.factory;

import java.util.HashMap;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class BFFactory {

	private static ORB orb;
	private static HashMap<String, Channel> mapChannel = new HashMap<String, Channel>();
	private static HashMap<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();
	private static HashMap<String, ChannelAdminCtr> mapChannelAdminCtr = new HashMap<String, ChannelAdminCtr>();
	private static HashMap<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();

	public synchronized static ORB createOrb(String[] args, Properties props) {
		if (orb == null)
			orb = ORB.init(args, props);
		return orb;

	}

	public synchronized static Channel createChannel(String topic, int capacity) {
		if (mapChannel.get(topic) == null)
			mapChannel.put(topic, new Channel(topic, capacity));
		return mapChannel.get(topic);
	}

	public synchronized static Channel forceChannel(String topic,
			Channel channel) {
		if (mapChannel.get(topic) != null) {
			mapChannel.remove(topic);
		}
		mapChannel.put(topic, channel);
		return mapChannel.get(topic);
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

	public synchronized static ChannelCtr createChannelCtr(String topic) {
		if (mapChannelCtr.get(topic) == null)
			mapChannelCtr.put(topic, new ChannelCtr(topic));
		return mapChannelCtr.get(topic);
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

	public synchronized static ChannelAdminCtr createChannelAdminCtr(
			String topic) {
		if (mapChannelAdminCtr.get(topic) == null)
			mapChannelAdminCtr.put(topic, new ChannelAdminCtr(topic));
		return mapChannelAdminCtr.get(topic);

	}

	public synchronized static ChannelAdminImpl createChannelAdminImpl(
			String topic) {
		if (mapChannelAdminImpl.get(topic) == null)
			mapChannelAdminImpl.put(topic, new ChannelAdminImpl(topic));
		return mapChannelAdminImpl.get(topic);
	}

	public synchronized static ChannelAdminImpl initiateChannel(String topic,
			int capacity) {
		createChannel(topic, capacity);
		return createChannelAdminImpl(topic);
	}

}
