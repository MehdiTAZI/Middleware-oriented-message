package fr.esiag.mezzodijava.mezzo.libclient;

import org.junit.Test;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;



public class MainConsumerIT {

	
	private String channelName="MEZZO";
	private String eventServerName="MEZZO-SERVER";
	
	public MainConsumerIT() throws EventClientException,
			TopicNotFoundException, ChannelNotFoundException,
			AlreadyRegisteredException, ChannelAlreadyExistsException, NotRegisteredException, InterruptedException, MaximalConnectionReachedException, AlreadyConnectedException, NotConnectedException {
		
		EventClient ec = EventClient.init(null);
		
		
		//creation du channel 
	/*	EventServerChannelAdmin eventServerChannelAdmin=ec.resolveEventServerChannelAdminByEventServerName(eventServerName);
		long id=eventServerChannelAdmin.createChannel(channelName, 10);
		System.out.println("Creation du channel "+channelName);
		System.out.println("UID "+ id);
		*/
		//l'abonnement et la connexion du consumer au channel crée
		
		ChannelAdmin channelAdmin =ec.resolveChannelByTopic(channelName);
		ProxyForPushConsumer consumerProxy = channelAdmin.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
		CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy.subscribe(cbc);
		Thread.sleep(10000);
		consumerProxy.connect();
		
		
		ProxyForPushConsumer consumerProxy1 = channelAdmin.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl1 = new callBackConsumerImpl();
		CallbackConsumer cbc1 = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy1.subscribe(cbc);
		Thread.sleep(15000);
		consumerProxy1.connect();
		
		
		
		ProxyForPushConsumer consumerProxy2 = channelAdmin.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl12 = new callBackConsumerImpl();
		CallbackConsumer cbc2 = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy2.subscribe(cbc);
		Thread.sleep(10000);
		consumerProxy2.connect();
		
		
		
		consumerProxy.disconnect();
		consumerProxy.unsubscribe();
		
		consumerProxy1.disconnect();
		consumerProxy1.unsubscribe();
		
		consumerProxy2.disconnect();
		consumerProxy2.unsubscribe();
		Thread.sleep(10000);
		
		
		
		
		System.out.println("ALL DONE");
		ORB orb = BFFactory.createOrb(null, null);
		orb.run();
	}

	public static void main(String[] args) throws ChannelNotFoundException,
			AlreadyRegisteredException, EventClientException,
			TopicNotFoundException, ChannelAlreadyExistsException, NotRegisteredException, InterruptedException, MaximalConnectionReachedException, AlreadyConnectedException, NotConnectedException {
						new MainConsumerIT();
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
