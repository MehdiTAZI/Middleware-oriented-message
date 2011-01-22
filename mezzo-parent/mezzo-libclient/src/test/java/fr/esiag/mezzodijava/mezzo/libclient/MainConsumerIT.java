package fr.esiag.mezzodijava.mezzo.libclient;

import org.junit.Test;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class MainConsumerIT {

	public MainConsumerIT() throws EventClientException,
			TopicNotFoundException, ChannelNotFoundException,
			AlreadyRegisteredException {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
		ProxyForPushConsumer consumerProxy = channelAdmin
				.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
		CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy.subscribe(cbc);
		try {
			consumerProxy.connect();
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

		System.out.println("ALL DONE");
		ORB orb = BFFactory.createOrb(null, null);
		orb.run();
	}

	public static void main(String[] args) throws ChannelNotFoundException,
			AlreadyRegisteredException, EventClientException,
			TopicNotFoundException {
		for (int i = 0; i < 10; i++) {
			new Thread(i + "") {
				{
					start();
				}
				public void run(){
					try {
						new MainConsumerIT();
					} catch (ChannelNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (EventClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TopicNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (AlreadyRegisteredException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};

		}


	}

	@Test
	public void testConsumerPush() throws Exception {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
		ProxyForPushConsumer consumerProxy = channelAdmin
				.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
		CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy.subscribe(cbc);
		System.out.println("ALL DONE");
	}

}
