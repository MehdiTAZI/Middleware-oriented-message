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
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public class TestChannelCtr {

    private static ChannelCtr channelCtr;
    private static String topic;
    private static int channelId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	CosEventServer.initConf();
	String topic = "bla";
	EventServer.getInstance().createChannelEntity(topic, 3);
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
	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		2);
	channelCtr = BFFactory.createChannelCtr(topic);
	// ajout manuel du channel pour la correspondance channel-composant
	channelId = DAOFactory.getJdbcDAO().insertChannel(channel);
    }

    @After
    public void tearDown() throws Exception {
	// suppression manuelle du channel
	DAOFactory.getJdbcDAO().deleteChannel(channelId);
    }

    @Test
    public void testAddProxyForPushConsumerToSubscribedListAlreadyRegistered() {

	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	channelCtr.addProxyForPushConsumerToSubscribedList(ppc);

    }

    @Test
    public void testRemoveProxyForPushConsumerFromConnectedListNormal()
	    throws AlreadyConnectedException,
	    MaximalConnectionReachedException, NotRegisteredException,
	    NotConnectedException, AlreadyRegisteredException {
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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

	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
	channelCtr.removeProxyForPushConsumerFromSubscribedList(ppc);
    }

    @Test
    public void testRemoveProxyForPushConsumerFromSubscribedListNotRegistered()
	    throws AlreadyRegisteredException {

	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
	channelCtr.addProxyForPushConsumerToConnectedList(ppc);
    }

    @Test
    public void testAddProxyForPushConsumerToConnectedListMaximalConnectionReached()
	    throws AlreadyRegisteredException, NotRegisteredException,
	    AlreadyConnectedException {
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	ProxyForPushConsumerImpl ppc2 = new ProxyForPushConsumerImpl(topic,
		"testconsumer2");
	ProxyForPushConsumerImpl ppc3 = new ProxyForPushConsumerImpl(topic,
		"testconsumer3");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
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
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
	try {
	    Channel channel = EventServer.getInstance().createChannelEntity(
		    topic, 0);
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
	ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,
		"testsupplier");
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
	ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,
		"testsupplier");
	ProxyForPushSupplierImpl pps2 = new ProxyForPushSupplierImpl(topic,
		"testsupplier2");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
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
	ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,
		"testsupplier");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(30);
	channelCtr.addProxyForPushSupplierToConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPushSupplierFromConnectedListNormal()
	    throws AlreadyConnectedException,
	    MaximalConnectionReachedException, NotConnectedException {
	ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,
		"testsupplier");
	channelCtr.addProxyForPushSupplierToConnectedList(pps);
	channelCtr.removeProxyForPushSupplierFromConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPushSupplierFromConnectedListNotConnected()
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	ProxyForPushSupplierImpl pps = new ProxyForPushSupplierImpl(topic,
		"testsupplier");
	try {
	    channelCtr.removeProxyForPushSupplierFromConnectedList(pps);
	    fail();
	} catch (NotConnectedException e) {
	    // test ok nothing to do
	}
    }

    @Test
    public void testAddProxyForPullSupplierToConnectedListAlreadyConnected()
	    throws MaximalConnectionReachedException {
	ProxyForPullSupplierImpl pps = new ProxyForPullSupplierImpl(topic,
		"testsupplier");
	try {
	    channelCtr.addProxyForPullSupplierToConnectedList(pps);
	    channelCtr.addProxyForPullSupplierToConnectedList(pps);
	    fail();
	} catch (AlreadyConnectedException e) {
	    // nothing to do test ok
	}
    }

    @Test
    public void testAddProxyForPullSupplierToConnectedListMaximalConnectionReached()
	    throws AlreadyConnectedException {
	ProxyForPullSupplierImpl pps = new ProxyForPullSupplierImpl(topic,
		"testsupplier");
	ProxyForPullSupplierImpl pps2 = new ProxyForPullSupplierImpl(topic,
		"testsupplier2");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(1);
	try {
	    channelCtr.addProxyForPullSupplierToConnectedList(pps);
	    channelCtr.addProxyForPullSupplierToConnectedList(pps2);
	    fail();
	} catch (MaximalConnectionReachedException e) {
	    // nothing to do test ok
	}
    }

    @Test
    public void testAddProxyForPullSupplierToConnectedListNormal()
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	ProxyForPullSupplierImpl pps = new ProxyForPullSupplierImpl(topic,
		"testsupplier");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(30);
	channelCtr.addProxyForPullSupplierToConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPullSupplierFromConnectedListNormal()
	    throws AlreadyConnectedException,
	    MaximalConnectionReachedException, NotConnectedException {
	ProxyForPullSupplierImpl pps = new ProxyForPullSupplierImpl(topic,
		"testsupplier");
	channelCtr.addProxyForPullSupplierToConnectedList(pps);
	channelCtr.removeProxyForPullSupplierFromConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPullSupplierFromConnectedListNotConnected()
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	ProxyForPullSupplierImpl pps = new ProxyForPullSupplierImpl(topic,
		"testsupplier");
	try {
	    channelCtr.removeProxyForPullSupplierFromConnectedList(pps);
	    fail();
	} catch (NotConnectedException e) {
	    // test ok nothing to do
	}
    }

    @Test
    public void testAddProxyForPullConsumerToConnectedListAlreadyConnected()
	    throws MaximalConnectionReachedException {
	ProxyForPullConsumerImpl pps = new ProxyForPullConsumerImpl(topic,
		"testsupplier");
	try {
	    channelCtr.addProxyForPullConsumerToConnectedList(pps);
	    channelCtr.addProxyForPullConsumerToConnectedList(pps);
	    fail();
	} catch (AlreadyConnectedException e) {
	    // nothing to do test ok
	}
    }

    @Test
    public void testAddProxyForPullConsumerToConnectedListMaximalConnectionReached()
	    throws AlreadyConnectedException {
	ProxyForPullConsumerImpl pps = new ProxyForPullConsumerImpl(topic,
		"testsupplier");
	ProxyForPullConsumerImpl pps2 = new ProxyForPullConsumerImpl(topic,
		"testsupplier2");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(1);
	try {
	    channelCtr.addProxyForPullConsumerToConnectedList(pps);
	    channelCtr.addProxyForPullConsumerToConnectedList(pps2);
	    fail();
	} catch (MaximalConnectionReachedException e) {
	    // nothing to do test ok
	}
    }

    @Test
    public void testAddProxyForPullConsumerToConnectedListNormal()
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	ProxyForPullConsumerImpl pps = new ProxyForPullConsumerImpl(topic,
		"testsupplier");

	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(30);
	channelCtr.addProxyForPullConsumerToConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPullConsumerFromConnectedListNormal()
	    throws AlreadyConnectedException,
	    MaximalConnectionReachedException, NotConnectedException {
	ProxyForPullConsumerImpl pps = new ProxyForPullConsumerImpl(topic,
		"testsupplier");
	channelCtr.addProxyForPullConsumerToConnectedList(pps);
	channelCtr.removeProxyForPullConsumerFromConnectedList(pps);
    }

    @Test
    public void testRemoveProxyForPullConsumerFromConnectedListNotConnected()
	    throws AlreadyConnectedException, MaximalConnectionReachedException {
	ProxyForPullConsumerImpl pps = new ProxyForPullConsumerImpl(topic,
		"testsupplier");
	try {
	    channelCtr.removeProxyForPullConsumerFromConnectedList(pps);
	    fail();
	} catch (NotConnectedException e) {
	    // test ok nothing to do
	}
    }

    @Test
    public void testAddEvent() throws AlreadyRegisteredException {
	Channel channel = EventServer.getInstance().createChannelEntity(topic,
		0);
	channel.setCapacity(2);
	ProxyForPushConsumerImpl ppc = new ProxyForPushConsumerImpl(topic,
		"testconsumer");
	channelCtr.addProxyForPushConsumerToSubscribedList(ppc);
	Header header = new Header(123, 1, 01012011, 120);

	ORB orb = ORB.init();
	Any any = orb.create_any();
	any.insert_string("Test_EVENT");
	Body body = new Body(any, "String");
	channelCtr.addEvent(new Event(header, body));
    }

    /*
     * @Test public void testGetEventNull() { Channel channel =
     * EventServer.getInstance().createChannelEntity(topic, 0);
     * channel.setCapacity(2); ProxyForPullConsumerImpl ppc = new
     * ProxyForPullConsumerImpl(topic,"testconsumer"); Event ev=null; try {
     * channelCtr.addProxyForPullConsumerToConnectedList(ppc); Header header =
     * new Header(123, 1, 01012011, 2000); ORB orb=ORB.init(); Any
     * any=orb.create_any(); any.insert_string("Test_EVENT"); Body body = new
     * Body(any,"String"); ev = new Event(header, body);
     * channelCtr.addEvent(ev); } catch (AlreadyConnectedException e) { fail();
     * } catch (MaximalConnectionReachedException e) { fail(); } try {
     * assertEquals(ev,channelCtr.getEvent(ppc)); } catch (NotConnectedException
     * e) { fail(); }
     *
     * }
     *
     * @Test public void testGetEvent() { Channel channel =
     * EventServer.getInstance().createChannelEntity(topic, 0);
     * channel.setCapacity(2); ProxyForPullConsumerImpl ppc = new
     * ProxyForPullConsumerImpl(topic,"testconsumer"); Event ev=null; try {
     * channelCtr.addProxyForPullConsumerToConnectedList(ppc); Header header =
     * new Header(123, 1, 01012011, 2000); ORB orb=ORB.init(); Any
     * any=orb.create_any(); any.insert_string("Test_EVENT"); Body body = new
     * Body(any,"String"); ev = new Event(header, body);
     * channelCtr.addEvent(ev); } catch (AlreadyConnectedException e) { fail();
     * } catch (MaximalConnectionReachedException e) { fail(); } try {
     * assertEquals
     * (ev.body.content.extract_string(),channelCtr.getEvent(ppc).body
     * .content.extract_string()); } catch (NotConnectedException e) { fail(); }
     *
     * }
     */
}
