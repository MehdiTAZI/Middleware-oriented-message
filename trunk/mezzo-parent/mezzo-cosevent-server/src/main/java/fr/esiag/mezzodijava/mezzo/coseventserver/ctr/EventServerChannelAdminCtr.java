package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Properties;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
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

    private String eventServerName;
    private Properties props = new Properties();

    /**
     * Fill the property eventServerName
     * 
     * @param eventServerName
     *            Name of the server
     */
    public EventServerChannelAdminCtr(String eventServerName) {
	this.eventServerName = eventServerName;

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
	this.eventServerName = topic;
	long channelPassword = BFFactory.createChannel(topic, capacity);
	System.out.println("Event Channel \"" + topic + "\" created.");
	return channelPassword;
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
	ChannelAdmin ca = BFFactory.getChannelAdmin(uniqueServerChannelId);
	if (ca == null) {
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
	Channel channel = BFFactory.getChannel(uniqueServerChannelId);
	if (channel != null) {
	    String topic = channel.getTopic();
	    ChannelCtr channelCtr = BFFactory.getChannelctr(topic);
	    ChannelPublisher.destroy();
	    channelCtr.removeAllProxiesForPushConsumerFromSubscribedList();
	    channelCtr.removeAllProxiesForPushConsumerFromConnectedList();
	    channelCtr.removeAllProxiesForPushSupplierFromConnectedList();
	    try {
		NamingContextExt nc = NamingContextExtHelper.narrow(BFFactory
			.getOrb().resolve_initial_references("NameService"));
		nc.unbind(nc.to_name(channel.getTopic()));
	    } catch (InvalidName e) {
		e.printStackTrace();
	    } catch (NotFound e) {
		e.printStackTrace();
	    } catch (CannotProceed e) {
		e.printStackTrace();
	    } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
		e.printStackTrace();
	    } catch (Exception e) {
		;
	    }
	    BFFactory.destroy(uniqueServerChannelId);
	    System.out.println("Event Channel \"" + topic + "\" destroyed.");
	} else
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException(
		    "Channel doesn't exist : id=" + uniqueServerChannelId);
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

	Channel channel = BFFactory.getChannel(uniqueServerChannelId);
	if (channel == null)
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();

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
	if (capacity < nbMaxConnected)
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException(
		    "Cannot reduce current channel capacity : verify that's the number of consumers/suppliers is lower than the capacity you want to apply !");
	else if (channelCapacity < nbMaxConnected)
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException(
		    "Internal channel configuration error !");

	BFFactory.changeChannelCapacity(channel, capacity);
	System.out.println("Event Channel \"" + channel.getTopic()
		+ "\" capacity updated to \"" + capacity + "\".");
    }
}