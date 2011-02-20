package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.PriorityQueue;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ThreadRemoveExpiredEvent;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

/**
 * Classe ProxyForPushSupplierImpl
 * 
 * Proxy for pushing Events, acts as a Consumer accessible to a client,
 * implementation of the ProxyForPushSupplier IDL Interface
 * 
 * UC n°: US14 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */

public class ProxyForPushSupplierImpl implements ProxyForPushSupplierOperations {

	/**
	 * The Channel Controller used by this facade
	 */
	private ChannelCtr channelCtr;

	private boolean connected = false;

	/**
	 * Build a ProxyForPushSupplier instance associated with the given topic and
	 * build the underlying Channel Controller.
	 * 
	 * @param topic
	 *            Channel Topic.
	 */
	public ProxyForPushSupplierImpl(String topic) {
		channelCtr = BFFactory.createChannelCtr(topic);
		ThreadRemoveExpiredEvent th=new ThreadRemoveExpiredEvent(channelCtr.getChannel().getQueueEvents(), channelCtr.getSynchronizedDate());
		Thread thread=new Thread(th);
		thread.start();
	}

	/**
	 * Connect this supplier to the the channel.
	 * 
	 * It will be able to push events.
	 * 
	 * @throws AlreadyConnectedException
	 *             If already present in the list.
	 * @throws MaximalConnectionReachedException
	 *             If Channel Connection Capaciy is reached.
	 */
	@Override
	public void connect() throws AlreadyConnectedException,
	MaximalConnectionReachedException {
		channelCtr.addProxyForPushSupplierToConnectedList(this);
		connected = true;
		System.out.println("Connect of a PUSH Supplier to \""
				+ channelCtr.getChannel().getTopic() + "\".");
	}

	/**
	 * Disconnect this Supplier from the channel.
	 * 
	 * It will no more be able to push events.
	 * 
	 * @throws NotConnectedException
	 *             If The Supplier was not connected.
	 */
	@Override
	public void disconnect() throws NotConnectedException {
		channelCtr.removeProxyForPushSupplierFromConnectedList(this);
		connected = false;
		System.out.println("Disconnect of a PUSH Consumer from \""
				+ channelCtr.getChannel().getTopic() + "\".");
	}

	/**
	 * To allow supplier to push Events to the Channel.
	 * 
	 * @param evt
	 *            Event to PUSH.
	 * @throws NotConnectedException
	 *             If The Supplier was not connected.
	 */
	@Override
	public void push(Event evt) throws NotConnectedException {
		
		if (!connected) {
			throw new NotConnectedException();
		}

		channelCtr.addEvent(evt);
		if(evt.header.date  <= channelCtr.getSynchronizedDate().getTime() && evt.header.date  >= channelCtr.getSynchronizedDate().getTime()+20){
			evt.header.date=channelCtr.getSynchronizedDate().getTime();
			channelCtr.getChannel().getQueueEvents().add(evt);
		}

	}

	// cette methode juste pour afficher la queue trier et apres on peut la supprimer
	
	public void afficher(){
				
			for(Event e: channelCtr.getChannel().getQueueEvents()){				
				System.out.println("QUEUE CAPACITY --> " + e.body.content +  "        priority : " + e.header.priority);				
			}

	}
}
