package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.fail;

import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ThreadEvent;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;

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
		channel.addSubscribedConsumer("testconsumerthread");
		// adding the mock as connected consumer
		channel.getConsumersConnected().put("testconsumerthread",ppfc);
		// adding an event
		Header header=new Header(123, 1, Calendar.getInstance().getTimeInMillis(), 1200);
		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_EVENT");
		Body body = new Body(any,"String");
		
		Event e = new Event(header,body);
		for (ConsumerModel consumer : channel
				.getConsumers().values()) {
		    consumer.getEvents().add((new EventConvertor()).transformToEventModel(e));
		}
		// receive is expected
		try {
			ppfc.receive(e);
		} catch (ConsumerNotFoundException e1) {
			fail("exception levée");
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
		channel.addSubscribedConsumer("testconsumerthread");
		// adding an event
		Header header=new Header(123, 1, 01012011, 120);
		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_EVENT");
		Body body = new Body(any,"String");
		
		Event e = new Event(header,body);
		for (ConsumerModel consumer : channel
			.getConsumers().values()) {
	    consumer.getEvents().add((new EventConvertor()).transformToEventModel(e));
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
		channel.addSubscribedConsumer("testconsumerthread");
		// adding the mock as connected consumer
		channel.getConsumersConnected().put("testconsumerthread",ppfc);
		// adding an event
		Header header=new Header(123, 1, Calendar.getInstance().getTimeInMillis(), 120);
		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_EVENT");
		Body body = new Body(any,"String");
		
		Event e = new Event(header,body);
		for (ConsumerModel consumer : channel
			.getConsumers().values()) {
	    consumer.getEvents().add((new EventConvertor()).transformToEventModel(e));
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
