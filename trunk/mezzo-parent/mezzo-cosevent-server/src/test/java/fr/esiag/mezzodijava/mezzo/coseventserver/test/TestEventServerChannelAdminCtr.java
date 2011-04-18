package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

@SuppressWarnings("static-access")
public class TestEventServerChannelAdminCtr {

	private static EventServerChannelAdminCtr ctr;
	private static BFFactory factory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = EasyMock.createMock(BFFactory.class);
		ctr = new EventServerChannelAdminCtr();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createChannel() throws ChannelAlreadyExistsException {
		EasyMock.replay(factory);
		long id = ctr.createChannel("MEZZO1", 30);
		EasyMock.verify(factory);
		assertEquals(EventServer.getInstance().getChannel("MEZZO1").getIdentifier(), id);
		assertEquals(EventServer.getInstance().getChannel(id).getCapacity(), 30);
	}

	@Test
	public void getChannel() throws ChannelAlreadyExistsException,
			ChannelNotFoundException {
		long id = ctr.createChannel("MEZZO2", 30);
		assertNotNull(ctr.getChannel(id));
	}

	@Test
	public void destroyChannel() throws ChannelAlreadyExistsException,
			ChannelNotFoundException {
		long id = ctr.createChannel("MEZZO3", 30);
		assertEquals(EventServer.getInstance().getChannel("MEZZO3").getIdentifier(), id);
		assertEquals(EventServer.getInstance().getChannel("MEZZO3").getCapacity(), 30);
		ctr.destroyChannel(id);
		try {
			ctr.getChannel(id);
			fail("Channel still exist");
		} catch (ChannelNotFoundException e) {
			;// OK Nothing to do
		}

	}

	@Test
	public void destroyNotFoundChannel() throws ChannelAlreadyExistsException {
		long id = ctr.createChannel("MEZZO3", 30);
		assertEquals(EventServer.getInstance().getChannel("MEZZO3").getIdentifier(), id);
		assertEquals(EventServer.getInstance().getChannel("MEZZO3").getCapacity(), 30);
		try {
			ctr.destroyChannel(id + 500000);
			fail("exception non levée");
		} catch (ChannelNotFoundException e1) {
			assertTrue(true);
		}
	}

	@Test
	public void changeChannelCapacity() throws ChannelAlreadyExistsException,
			ChannelNotFoundException, CannotReduceCapacityException {
		long id = ctr.createChannel("MEZZO4", 30);
		assertEquals(EventServer.getInstance().getChannel("MEZZO4").getCapacity(), 30);
		ctr.changeChannelCapacity(id, 40);
		assertEquals(EventServer.getInstance().getChannel(id).getCapacity(), 40);
	}

	@Test
	public void changeChannelCapacityButNotFound()
			throws ChannelAlreadyExistsException {
		long id = ctr.createChannel("MEZZO5", 30);
		assertEquals(EventServer.getInstance().getChannel("MEZZO5").getCapacity(), 30);
		try {
			ctr.changeChannelCapacity(id + 500000, 40);
			fail("exception non levée");
		} catch (ChannelNotFoundException e) {
			assertTrue(true);
		} catch (CannotReduceCapacityException e) {
			fail("mauvaise exception levée");
		}
	}

	@Test
	public void changeChannelCapacityButCannotReduce()
			throws ChannelAlreadyExistsException, AlreadyConnectedException,
			NotRegisteredException, MaximalConnectionReachedException,
			ChannelNotFoundException {
		
		long id = ctr.createChannel("MEZZO6", 30);
		assertEquals(EventServer.getInstance().getChannel("MEZZO6").getCapacity(), 30);
		Channel channel = EventServer.getInstance().getChannel(id);
		// BFFactory.setAlternateChannel("TOPIC_TEST", channel);
		ProxyForPushConsumerImpl ppfc = EasyMock
				.createStrictMock(ProxyForPushConsumerImpl.class);
		// adding the mock as subscribed consumer
		channel.addSubscribedConsumer("testchange");
		// adding the mock as connected consumer
		channel.getConsumersConnected().put("testchange",ppfc);
		try {
			ctr.changeChannelCapacity(id, 0);
			fail("exception non levée");
		} catch (CannotReduceCapacityException e) {
			assertTrue(true);
		}
	}
}
