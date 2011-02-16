package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ThreadEvent;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class TestThreadEvent {

	private ThreadEvent threadEvent;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRunWithConsumerSubscribedAndConnected() {
		// pour que l'appel getChannel.getTopic() fonctionne
		// EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST",
		// 1));

		Channel channel = new Channel("TEST", 1);
		BFFactory.setAlternateChannel("TOPIC_TEST", channel);
		threadEvent = new ThreadEvent("TOPIC_TEST");
		ProxyForPushConsumerImpl ppfc = EasyMock
				.createStrictMock(ProxyForPushConsumerImpl.class);
		// adding the mock as subscribed consumer
		channel.addSubscribedConsumer(ppfc);
		// adding the mock as connected consumer
		channel.getConsumersConnected().add(ppfc);
		// adding an event
		Event e = new Event(0, "TEST_EVENT",0,4567);
		for (ProxyForPushConsumerImpl consumer : channel
				.getConsumersSubscribed().keySet()) {
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
		// receive is expected
		try {
			ppfc.receive(e);
		} catch (ConsumerNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Passage du mock en mode de test
		EasyMock.replay(ppfc);
		// Test
		threadEvent.processSubscribedConsumers();
		// vérification de l'appel à la méthode receive
		EasyMock.verify(ppfc);
	}

	@Test
	public void testRunWithConsumerSubscribedAndNotConnected() {
		// pour que l'appel getChannel.getTopic() fonctionne
		// EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST",
		// 1));

		Channel channel = new Channel("TEST", 1);
		BFFactory.setAlternateChannel("TOPIC_TEST", channel);
		threadEvent = new ThreadEvent("TOPIC_TEST");
		ProxyForPushConsumerImpl ppfc = EasyMock
				.createStrictMock(ProxyForPushConsumerImpl.class);
		// adding the mock as subscribed consumer
		channel.addSubscribedConsumer(ppfc);
		// adding an event
		Event e = new Event(0, "TEST_EVENT",0,456789);
		for (ProxyForPushConsumerImpl consumer : channel
				.getConsumersSubscribed().keySet()) {
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
		// Passage du mock en mode de test
		EasyMock.replay(ppfc);
		// Test
		threadEvent.processSubscribedConsumers();
		// vérification de l'appel à la méthode receive
		EasyMock.verify(ppfc);
	}

	@Test
	public void testRunWithConsumerSubscribedAndConnectedButUnreachable() throws NotConnectedException, NotRegisteredException, ConsumerNotFoundException {
		// pour que l'appel getChannel.getTopic() fonctionne
		// EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST",
		// 1));

		Channel channel = new Channel("TEST", 1);
		BFFactory.setAlternateChannel("TOPIC_TEST", channel);
		threadEvent = new ThreadEvent("TOPIC_TEST");
		ProxyForPushConsumerImpl ppfc = EasyMock
				.createStrictMock(ProxyForPushConsumerImpl.class);
		// adding the mock as subscribed consumer
		channel.addSubscribedConsumer(ppfc);
		// adding the mock as connected consumer
		channel.getConsumersConnected().add(ppfc);
		// adding an event
		Event e = new Event(0, "TEST_EVENT",0,45452);
		for (ProxyForPushConsumerImpl consumer : channel
				.getConsumersSubscribed().keySet()) {
			channel.getConsumersSubscribed().get(consumer).add(e);
		}
		// on s'attend à ce qu'un appel à receive soit fait
		ppfc.receive(e);

		// Le mock doit renvoye l'exception
		EasyMock.expectLastCall().andThrow(new ConsumerNotFoundException());
		// Et donc on s'attend à ce qu'un appel à disconnect soit fait
		ppfc.disconnect();

		// Passage du mock en mode de test
		EasyMock.replay(ppfc);
		// Test
		threadEvent.processSubscribedConsumers();
		// vérification de l'appel à la méthode receive
		EasyMock.verify(ppfc);
	}

}
