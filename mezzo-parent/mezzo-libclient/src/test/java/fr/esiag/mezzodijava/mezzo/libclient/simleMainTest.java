package fr.esiag.mezzodijava.mezzo.libclient;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class simleMainTest {

	public simleMainTest() throws EventClientException, TopicNotFoundException, ChannelNotFoundException, AlreadyRegisteredException
	{
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin= ec.resolveChannelByTopic("MEZZO");
		ProxyForPushConsumer consumerProxy = channelAdmin.getProxyForPushConsumer();
		callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
		CallbackConsumer cbc=ec.serveCallbackConsumer(callbackImpl);
		consumerProxy.subscribe(cbc);
		System.out.println("ALL DONE");
	}
	public static void main(String[] args) throws ChannelNotFoundException, AlreadyRegisteredException, EventClientException, TopicNotFoundException {
		new simleMainTest();

	}

}
