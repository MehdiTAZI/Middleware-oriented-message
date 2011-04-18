package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.publisher.ChannelPublisher;

/**
 * Classe EventServerChannelAdminCtr
 * 
 * For controlling channel creation and destruction
 * 
 * UC n°07: US10 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */

public class EventServerChannelAdminCtr {

	private static Logger log = LoggerFactory
			.getLogger(EventServerChannelAdminCtr.class);

	/**
	 * Fill the property eventServerName
	 * 
	 * @param eventServerName
	 *            Name of the server
	 */
	public EventServerChannelAdminCtr(String eventServerName) {
		log.trace("Creation of an EventServerChannelAdminCtr {}",
				eventServerName);

	}

	/**
	 * Empty constructor
	 * 
	 */
	public EventServerChannelAdminCtr() {
	}

	/**
	 * Build a Channel with an initial capacity and topic
	 * 
	 * @param topic
	 *            Channel Name
	 * @param capacity
	 *            Number of composants connected
	 * @return the unique identifier of the channel
	 */
	public long createChannel(String topic, int capacity)
			throws ChannelAlreadyExistsException {
		log.trace("create channel {}. Capacity {}", topic, capacity);
		Channel channel = BFFactory.createChannel(topic, capacity);
		// persistance du channel
		DAOFactory.getJdbcDAO().insertChannel(channel);
		log.debug("Event Channel \"" + topic + "\" created.");
		return channel.getIdentifier();
	}

	/**
	 * Find the Channel identified with an uid
	 * 
	 * @param uniqueServerChannelId
	 *            The uid of the channel to find
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist
	 * @return the Channel's Administrator
	 * 
	 */
	public ChannelAdmin getChannel(long uniqueServerChannelId)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		log.trace("access to channelAdmin number {}", uniqueServerChannelId);
		ChannelAdmin ca = BFFactory.getChannelAdmin(uniqueServerChannelId);
		if (ca == null) {
			log.error(
					"Can't accessed to channelAdmin number {} because it doesn't exist",
					uniqueServerChannelId);
			throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException(
					"Channel doesn't exist : id=" + uniqueServerChannelId);
		}
		return ca;
	}

	/**
	 * Destroy the Channel identified with an uid; delete the channel and all
	 * the list of events and proxies
	 * 
	 * @param uniqueServerChannelId
	 *            The uid of the channel to destroy
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist
	 */
	public void destroyChannel(long uniqueServerChannelId)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
		log.info("Destruction of channel number {}", uniqueServerChannelId);
		Channel channel = EventServer.getInstance().getChannel(
				uniqueServerChannelId);
		if (channel != null) {
			log.debug("channel {} exists", uniqueServerChannelId);
			String topic = channel.getTopic();
			ChannelCtr channelCtr = BFFactory.getChannelctr(topic);
			ChannelPublisher.destroy(BFFactory.createChannelAdminImpl(topic));
			channelCtr.removeAllProxiesForPushConsumerFromSubscribedList();
			channelCtr.removeAllProxiesForPushConsumerFromConnectedList();
			channelCtr.removeAllProxiesForPushSupplierFromConnectedList();
			channelCtr.removeAllProxiesForPullConsumerFromConnectedList();
			channelCtr.removeAllProxiesForPullSupplierFromConnectedList();
			BFFactory.destroy(uniqueServerChannelId);
			// suppression du channel dans la base
			DAOFactory.getJdbcDAO().deleteChannel(channel.getId());
			log.info("Event Channel \"" + topic + "\" destroyed.");
		} else {
			log.error("channel {} doesn't exist", uniqueServerChannelId);
			throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException(
					"Channel doesn't exist : id=" + uniqueServerChannelId);
		}
	}

	/**
	 * Change the maximum number of composants connected at the same time
	 * 
	 * @param uniqueServerChannelId
	 *            The uid of the channel to find
	 * @param capacity
	 *            The new capacity to apply
	 * @throws ChannelNotFoundException
	 *             If the channel doesn't exist
	 * @throws CannotReduceCapacityException
	 *             If the channel is full
	 */
	public void changeChannelCapacity(long uniqueServerChannelId, int capacity)
			throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException,
			fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException {
		log.trace("change channel capacity");

		Channel channel = EventServer.getInstance().getChannel(
				uniqueServerChannelId);
		if (channel == null) {
			log.error("channel {} not found", uniqueServerChannelId);
			throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();

		}
		/*
		 * if (channel.getCapacity() > capacity) throw new
		 * fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException();
		 */

		int channelCapacity = channel.getCapacity();
		int nbConnectedSuppliers = channel.getSuppliersConnected().size();
		int nbConnectedConsumers = channel.getConsumersConnected().size();
		int nbMaxConnected = (nbConnectedSuppliers > nbConnectedConsumers) ? nbConnectedSuppliers
				: nbConnectedConsumers;

		// impossible a se produire sauf en cas de modification du code de
		// channel ...
		if (capacity < nbMaxConnected) {
			log.error("can't reduced capacity : too many connected");
			throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException(
					"Cannot reduce current channel capacity : verify that's the number of consumers/suppliers is lower than the capacity you want to apply !");
		} else if (channelCapacity < nbMaxConnected) {
			log.error("can't reduced capacity : channelCapacity < nbMaxConnected");
			throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException(
					"Internal channel configuration error !");
		}

		BFFactory.changeChannelCapacity(channel, capacity);
		// update dans la base de la capacité
		DAOFactory.getJdbcDAO().updateChannel(channel);
		log.debug("Event Channel \"" + channel.getTopic()
				+ "\" capacity updated to \"" + capacity + "\".");
	}
}