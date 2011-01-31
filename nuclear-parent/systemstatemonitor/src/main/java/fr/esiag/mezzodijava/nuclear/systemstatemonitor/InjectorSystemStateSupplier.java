package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class InjectorSystemStateSupplier {
	
	private ProxyForPushSupplier supplierProxy;
	
	public InjectorSystemStateSupplier() throws EventClientException,
	TopicNotFoundException, ChannelNotFoundException,
	AlreadyRegisteredException {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("injector system state");
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
			System.out.println("Message envoyé timestamp:" + e.timestamp
					+ ", contenu" + e.content);
			
		} catch (ChannelNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotConnectedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
