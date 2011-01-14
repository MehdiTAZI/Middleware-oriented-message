package fr.esiag.mezzodijava.mezzo.libclient;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;


public class MainSupplierIT {

	/**
	 * @param args
	 * @throws EventClientException 
	 * @throws TopicNotFoundException 
	 * @throws ChannelNotFoundException 
	 * @throws AlreadyConnectedException 
	 * @throws MaximalConnectionReachedException 
	 */
	private ORB orb;
	public MainSupplierIT() throws EventClientException, TopicNotFoundException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException{
		EventClient ec = EventClient.init(null);
		orb=ec.getOrb();
		ChannelAdmin channelAdmin= ec.resolveChannelByTopic("MEZZO");
		
		ProxyForPushSupplier supplierProxy = channelAdmin.getProxyForPushSupplier();			
		supplierProxy.connect();
		Any any=orb.create_any();
		any.insert_string("TEST");
		for(int i=0;i<3;i++)
			try {
				supplierProxy.push(new Event(i, any));
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		System.out.println("ALL DONE");
		
	}
	
	public static void main(String[] args) throws ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, EventClientException, TopicNotFoundException {
			new MainSupplierIT();

	}

}
