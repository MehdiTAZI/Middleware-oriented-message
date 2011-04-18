package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class NuclearSensorConsumer {

	public NuclearSensorConsumer(InjectorSystemStateSupplier supplier) throws EventClientException,
			TopicNotFoundException, ChannelNotFoundException {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("nuclear sensor");
		String idcomponent = "nuclear";
		ProxyForPushConsumer consumerProxy = channelAdmin
				.getProxyForPushConsumer(idcomponent);
		System.out.println("creation callback");
		CallBackConsumerImpl callbackImpl = new CallBackConsumerImpl(supplier);
		CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
		System.out.println("subscribe du consumer");
		try {
		    consumerProxy.subscribe();
		} catch (AlreadyRegisteredException e1) {
		   System.out.println("already subscribed so let's connect");
		}
		try {
			consumerProxy.connect(cbc);
			System.out.println("connexion nuclear sensor");
		} catch (NotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ALL DONE for consumer nuclear");
		ORB orb = ec.getOrb();
		orb.run();
	}

}
