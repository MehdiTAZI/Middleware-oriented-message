package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public class TestChannelCtr {

	private static ChannelCtr channelCtr;
	private static String topic;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String topic = "bla";
		Channel channel = EventServer.getInstance().createChannelEntity(topic, 3);
		channelCtr = BFFactory.createChannelCtr(topic);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Random rm = new Random();
		topic = rm.nextInt() + "";
		System.out.println(topic);
		Channel channel = EventServer.getInstance().createChannelEntity(topic, 2);
		channelCtr = BFFactory.createChannelCtr(topic);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddProxyForPushConsumerToSubscribedListAlreadyRegistered() {

		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		try {
			channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
			channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
			fail();
		} catch (AlreadyRegisteredException e) {
			// test ok, nothing to do
		}

	}

	@Test
	public void testAddProxyForPushConsumerToSubscribedListNormal()
			throws AlreadyRegisteredException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);

	}

	@Test
	public void testRemoveProxyForPushConsumerFromConnectedListNormal()
			throws AlreadyConnectedException,
			MaximalConnectionReachedException, NotRegisteredException,
			NotConnectedException, AlreadyRegisteredException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		// register
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		// connect
		channelCtr.addProxyForPushConsumerToConnectedList(ppc);
		// test !
		channelCtr.removeProxyForPushConsumerFromConnectedList(ppc);

	}

	@Test
	public void testRemoveProxyForPushConsumerFromConnectedListNotConnected()
			throws NotRegisteredException, AlreadyRegisteredException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		try {
			// register
			channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
			// test !
			channelCtr.removeProxyForPushConsumerFromConnectedList(ppc);
			fail();
		} catch (NotConnectedException e) {
			// test ok nothing to do
		}
	}

	@Test
	public void testRemoveProxyForPushConsumerFromConnectedListNotRegistered()
			throws NotConnectedException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		try {
			channelCtr.removeProxyForPushConsumerFromConnectedList(ppc);
			fail();
		} catch (NotRegisteredException e) {
			// test ok nothing to do
		}
	}

	@Test
	public void testRemoveProxyForPushConsumerFromSubscribedListNormal()
			throws AlreadyRegisteredException, NotRegisteredException {

		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		channelCtr.removeProxyForPushConsumerFromSubscribedList(ppc);
	}

	@Test
	public void testRemoveProxyForPushConsumerFromSubscribedListNotRegistered()
			throws AlreadyRegisteredException {

		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		try {
			channelCtr.removeProxyForPushConsumerFromSubscribedList(ppc);
			fail();
		} catch (NotRegisteredException e) {
			// test ok, nothing to do
		}
	}

	@Test
	public void testAddProxyForPushConsumerToConnectedListNormal()
			throws AlreadyRegisteredException, NotRegisteredException,
			AlreadyConnectedException, MaximalConnectionReachedException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		channelCtr.addProxyForPushConsumerToConnectedList(ppc);
	}

	@Test
	public void testAddProxyForPushConsumerToConnectedListMaximalConnectionReached()
			throws AlreadyRegisteredException, NotRegisteredException,
			AlreadyConnectedException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		ProxyForPushConsumerImpl ppc2 = new ProxyForPushConsumerImpl(topic,"testconsumer2");
		ProxyForPushConsumerImpl ppc3 = new ProxyForPushConsumerImpl(topic,"testconsumer3");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc2);
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc3);
		try {
			channelCtr.addProxyForPushConsumerToConnectedList(ppc);
			channelCtr.addProxyForPushConsumerToConnectedList(ppc2);
			channelCtr.addProxyForPushConsumerToConnectedList(ppc3);
			fail();
		} catch (MaximalConnectionReachedException e) {
			// test ok, nothing to do
		}
	}

	@Test
	public void testAddProxyForPushConsumerToConnectedListNotRegistered()
			throws AlreadyRegisteredException, AlreadyConnectedException,
			MaximalConnectionReachedException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		ProxyForPushConsumerImpl ppc2 = new ProxyForPushConsumerImpl(topic,"testconsumer2");
		ProxyForPushConsumerImpl ppc3 = new ProxyForPushConsumerImpl(topic,"testconsumer3");
		try {
			channelCtr.addProxyForPushConsumerToConnectedList(ppc);
			fail();
		} catch (NotRegisteredException e) {
			// test ok, nothing to do
		}
	}

	@Test
	public void testAddProxyForPushConsumerToConnectedListAlreadyConnected()
			throws AlreadyRegisteredException, NotRegisteredException,
			MaximalConnectionReachedException {
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		try {
			Channel channel = EventServer.getInstance().createChannelEntity(topic, 0);
			channel.setCapacity(10);
			channelCtr.addProxyForPushConsumerToConnectedList(ppc);
			channelCtr.addProxyForPushConsumerToConnectedList(ppc);
			fail();
		} catch (AlreadyConnectedException e) {
			// test ok nothing to do
		}

	}

	@Test
	public void testAddProxyForPushSupplierToConnectedListAlreadyConnected()
			throws MaximalConnectionReachedException {
		ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,"testsupplier");
		try {
			channelCtr.addProxyForPushSupplierToConnectedList(pps);
			channelCtr.addProxyForPushSupplierToConnectedList(pps);
			fail();
		} catch (AlreadyConnectedException e) {
			// nothing to do test ok
		}
	}

	@Test
	public void testAddProxyForPushSupplierToConnectedListMaximalConnectionReached()
			throws AlreadyConnectedException {
		ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,"testsupplier");
		ProxyForPushSupplierImpl pps2 = new ProxyForPushSupplierImpl(topic,"testsupplier2");

		Channel channel = EventServer.getInstance().createChannelEntity(topic, 0);
		channel.setCapacity(1);
		try {
			channelCtr.addProxyForPushSupplierToConnectedList(pps);
			channelCtr.addProxyForPushSupplierToConnectedList(pps2);
			fail();
		} catch (MaximalConnectionReachedException e) {
			// nothing to do test ok
		}
	}

	@Test
	public void testAddProxyForPushSupplierToConnectedListNormal()
			throws AlreadyConnectedException, MaximalConnectionReachedException {
		ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,"testsupplier");
		ProxyForPushSupplierImpl pps2 = new ProxyForPushSupplierImpl(topic,"testsupplier2");

		Channel channel = EventServer.getInstance().createChannelEntity(topic, 0);
		channel.setCapacity(30);
		channelCtr.addProxyForPushSupplierToConnectedList(pps);
	}

	@Test
	public void testRemoveProxyForPushSupplierFromConnectedListNormal()
			throws AlreadyConnectedException,
			MaximalConnectionReachedException, NotConnectedException {
		ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,"testsupplier");
		channelCtr.addProxyForPushSupplierToConnectedList(pps);
		channelCtr.removeProxyForPushSupplierFromConnectedList(pps);
	}

	@Test
	public void testRemoveProxyForPushSupplierFromConnectedListNotConnected()
			throws AlreadyConnectedException, MaximalConnectionReachedException {
		ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,"testsupplier");
		try {
			channelCtr.removeProxyForPushSupplierFromConnectedList(pps);
			fail();
		} catch (NotConnectedException e) {
			// test ok nothing to do
		}
	}

	@Test
	public void testAddEvent() throws AlreadyRegisteredException {
		// public void addEvent(Event e){
		// for(ProxyForPushConsumerImpl consumer
		// :channel.getConsumersSubscribed().keySet()){
		// channel.getConsumersSubscribed().get(consumer).add(e);
		// }
		// }
		Channel channel = EventServer.getInstance().createChannelEntity(topic, 0);
		channel.setCapacity(2);
		ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,"testconsumer");
		channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
		Header header = new Header(123, 1, 01012011, 120);
		
		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_EVENT");
		Body body = new Body(any,"String");
		channelCtr.addEvent(new Event(header, body));
	}
}
