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
 * UC n°: US10 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */

public class EventServerChannelAdminCtr {

    private String eventServerName;
    private Properties props = new Properties();

    public EventServerChannelAdminCtr(String eventServerName) {
	this.eventServerName = eventServerName;

    }

    public EventServerChannelAdminCtr() {
    }

    public long createChannel(String topic, int capacity)
	    throws ChannelAlreadyExistsException {
	this.eventServerName = topic;
	long channelPassword = BFFactory.createChannel(topic, capacity);
	System.out.println("Event Channel \"" + topic + "\" created.");
	return channelPassword;
    }

    public ChannelAdmin getChannel(long uniqueServerChannelId)
	    throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException {
	return BFFactory.getChannelAdmin(uniqueServerChannelId);
    }

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
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();
    }

    public void changeChannelCapacity(long uniqueServerChannelId, int capacity)
	    throws fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException,
	    fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException {

	Channel channel = BFFactory.getChannel(uniqueServerChannelId);
	if (channel == null)
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException();
	if (channel.getCapacity() > capacity)
	    throw new fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException();
	BFFactory.changeChannelCapacity(channel, capacity);
	System.out.println("Event Channel \"" + channel.getTopic()
		+ "\" capacity updated to \"" + capacity + "\".");

    }
}