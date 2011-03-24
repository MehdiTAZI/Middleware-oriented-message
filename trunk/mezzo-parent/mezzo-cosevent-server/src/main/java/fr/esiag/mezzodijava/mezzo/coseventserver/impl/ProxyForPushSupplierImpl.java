package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierOperations;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
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

public class ProxyForPushSupplierImpl extends AbstractProxyImpl implements ProxyForPushSupplierOperations {


	public ProxyForPushSupplierImpl(String topic) {
		super(topic);
		// TODO Auto-generated constructor stub
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

		

	}

	
	/**
	 * To print capacity and priority
	 */
	public void afficher(){
				//System.out.println("In Afficher "+channelCtr.getChannel().getQueueEvents().size());
			
//				Iterator<Event> iterator=channelCtr.getChannel().getQueueEvents().iterator();
//				while(iterator.hasNext()){
//					Event e=iterator.next();
//					System.out.println("QUEUE CAPACITY --> " + e.body.content +  "        priority : " + e.header.priority);
//				}
			
		for (int i = 0; i <channelCtr.getChannel().getQueueEvents().size(); i++) {
			Event e=channelCtr.getChannel().getQueueEvents().remove();
			System.out.println("QUEUE CAPACITY --> " + e.body.content +  "        priority : " + e.header.priority);
		}

	}
}
