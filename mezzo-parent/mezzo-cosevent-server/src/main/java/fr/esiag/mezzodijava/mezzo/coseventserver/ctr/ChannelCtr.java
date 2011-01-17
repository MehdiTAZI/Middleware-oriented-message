/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

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
	Channel channel;
	
	// Edit FGI : Vector -> Set synchronized car moderne

	
	/**
	 * Build instance of a ChannelCtr associated with a Channel entity
	 * 
	 * @param channel
	 *            Channel entity
	 */
	public ChannelCtr(Channel channel) {
		this.channel = channel;
	}
	public ChannelCtr(String channel) {
		this.channel = BFFactory.createChannel(channel,0);
		
	}
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channelModel) {
		this.channel = channelModel;
	}

	
	public void addProxyForPushConsumerToSubscribedList(
			ProxyForPushConsumerImpl proxyConsumer)throws AlreadyRegisteredException {
		if(channel.getConsumersSubscribed().containsKey(proxyConsumer))
			throw new AlreadyRegisteredException();
		else
			channel.getConsumersSubscribed().put(proxyConsumer, new ArrayList<Event>());	
		}
	
	public void removeProxyForPushConsumerFromSubscribedList(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotRegisteredException{
		if(!channel.getConsumersSubscribed().containsKey(proxyConsumer))
			throw new NotRegisteredException();
		else
			channel.getConsumersSubscribed().remove(proxyConsumer);	
		}
	
	public void addProxyForPushConsumerToConnectedList(
			ProxyForPushConsumerImpl proxyConsumer) throws NotRegisteredException,AlreadyConnectedException,MaximalConnectionReachedException{
		if(!channel.getConsumersSubscribed().containsKey(proxyConsumer))
			throw new NotRegisteredException();
		if(!channel.ConsumersConnectedListcapacityReached())
			throw new MaximalConnectionReachedException();
		if (!channel.getConsumersConnected().add(proxyConsumer))
			throw new AlreadyConnectedException();
	}

	public void removeProxyForPushConsumerFromConnectedList(
			ProxyForPushConsumerOperations proxyConsumer)
			throws NotRegisteredException ,NotConnectedException{
		if(!channel.getConsumersSubscribed().containsKey(proxyConsumer))
			throw new NotRegisteredException();
		if (!channel.getConsumersConnected().remove(proxyConsumer)) {
			throw new NotConnectedException();
		}
	}

	//TMA : un Supplier ne s'abonne pas
	/*
	public void addProxyForPushSupplierToSubscribedList(
			ProxyForPushSupplierImpl proxySupplier) throws AlreadyRegisteredException{
		if (!channel.getSuppliersSubscribed().add(proxySupplier)) {
			throw new AlreadyRegisteredException();
		}
	}
	*/
	public void addProxyForPushSupplierToConnectedList(
			ProxyForPushSupplierImpl proxySupplier)throws AlreadyConnectedException,MaximalConnectionReachedException{
		if(!channel.SuppliersConnectedsListcapacityReached())
			throw new MaximalConnectionReachedException();
		if (!channel.getSuppliersConnected().add(proxySupplier)) 
			throw new AlreadyConnectedException();
	}
	public void removeProxyForPushSupplierFromConnectedList(
			ProxyForPushSupplierOperations proxySupplier)
			throws NotConnectedException{
		if (!channel.getSuppliersConnected().remove(proxySupplier)) {
			throw new NotConnectedException();
		}
	}
	//TMA : un supplier ne s'abonne/desabonne pas
	/*
	public void removeProxyForPushSupplierFromSubscribedList(
			ProxyForPushSupplierOperations proxySupplier)
			throws NotRegisteredException {
		if (!channel.getSuppliersSubscribed().remove(proxySupplier)) 
			throw new NotRegisteredException();
	}*/

	public void addEvent(Event e) throws ChannelNotFoundException,NotConnectedException{
		for(ProxyForPushConsumerImpl consumer :channel.getConsumersSubscribed().keySet()){
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
	}

	
}
