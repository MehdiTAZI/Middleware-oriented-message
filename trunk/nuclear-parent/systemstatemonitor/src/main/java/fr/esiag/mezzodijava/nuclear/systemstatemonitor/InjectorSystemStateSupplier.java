package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class InjectorSystemStateSupplier {
	
	private ProxyForPushSupplier supplierProxy;
	
	public InjectorSystemStateSupplier() throws EventClientException,
	TopicNotFoundException, ChannelNotFoundException,
	AlreadyRegisteredException {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("nuclear sensor");
		supplierProxy = channelAdmin
		.getProxyForPushSupplier();
		
		try {
			supplierProxy.connect();
			System.out.println("supplier injector connecté");
		} catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ALL DONE for supplier injector");
		/*ORB orb = BFFactory.createOrb(null, null);
		orb.run();*/
	}

	public void PushEvent(Event e)
	{
		try {
			supplierProxy.push(e);
			System.out.println("Message envoyé timestamp:" + e.header.timestamp
					+ ", contenu" + e.body.content);
			
		} catch (ChannelNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotConnectedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
