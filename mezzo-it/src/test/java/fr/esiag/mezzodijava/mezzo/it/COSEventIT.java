package fr.esiag.mezzodijava.mezzo.it;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.costimeserver.main.CosTimeServer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TimeClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class COSEventIT {

    @BeforeClass
    public static void beforeClass() {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// PropertyConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.properties"));

    }

    public static Integer recu = 0;

    public static List<Event> messagesRecu = Collections
	    .synchronizedList(new ArrayList<Event>());

    public static class callBackConsumerImpl implements
	    CallbackConsumerOperations {

	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
	    System.out.println("recu " + evt);
	    synchronized (recu) {
		recu++;
	    }
	    messagesRecu.add(evt);
	}

    }

    public static void main(String[] args) throws ChannelNotFoundException,
	    AlreadyRegisteredException, EventClientException,
	    TopicNotFoundException, ChannelAlreadyExistsException,
	    NotRegisteredException, InterruptedException,
	    MaximalConnectionReachedException, AlreadyConnectedException,
	    NotConnectedException, TimeClientException {
	EventClient ec = EventClient.init(null);
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer();
	callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
	CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
	consumerProxy.subscribe(cbc);
	if ((args != null) && (args.length >= 1)) {
	    Thread.sleep(new Long(args[0]).longValue());
	}
	consumerProxy.connect();
	ec.getOrb().run();
	System.out.println("ALL DONE");
    }

    @Before
    public void beforeTest() throws InterruptedException {
	// mise a zero du compteur de message
	recu = 0;
	// mise blanc de la liste des messages reçus
	messagesRecu = Collections.synchronizedList(new ArrayList<Event>());
	// le time serveur
	MainServerLauncher sl = new MainServerLauncher(CosTimeServer.class,
		1000, "MEZZO-COSTIME", "1000");
	sl.go();
	// le event serveur
	MainServerLauncher s2 = new MainServerLauncher(CosEventServer.class,
		2000, "MEZZO-SERVER");
	s2.go();

    }

    @Test
    public void testSupplier() throws InterruptedException,
	    EventClientException, ChannelAlreadyExistsException,
	    TopicNotFoundException, ChannelNotFoundException,
	    NotConnectedException, MaximalConnectionReachedException,
	    AlreadyConnectedException {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class, 2000,
		(String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");

	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier();
	supplierProxy.connect();

	for (int i = 0; i < 10; i++) {
	    Header header = new Header(123, 1, Calendar.getInstance()
		    .getTimeInMillis(), 10120);
	    Body body = new Body("Test_EVENT_" + i);
	    Event evt = new Event(header, body);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	System.out.println("fini");
    }

    @Test
    public void testMultiSupplier() throws InterruptedException,
	    EventClientException, ChannelAlreadyExistsException,
	    TopicNotFoundException, ChannelNotFoundException,
	    NotConnectedException, MaximalConnectionReachedException,
	    AlreadyConnectedException {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class, 2000,
		(String[]) null);
	s2.go();
	// ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	for (int i = 0; i < 10; i++) {
	    Thread t = new Thread(i + "") {
		public void run() {
		    try {
			EventClient ec = EventClient.init(null);
			ChannelAdmin channelAdmin = ec
				.resolveChannelByTopic("MEZZO");
			ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier();
			supplierProxy.connect();

			for (int i = 0; i < 10; i++) {
			    Header header = new Header(123, 1, Calendar
				    .getInstance().getTimeInMillis(), 10120);
			    Body body = new Body("Test_EVENT_" + i);
			    Event evt = new Event(header, body);
			    supplierProxy.push(evt);
			    System.out.println("envoye " + evt);
			    Thread.sleep(100);
			}
		    } catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (EventClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (TopicNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		};
	    };
	    t.start();
	}
	Thread.sleep(5000);
	esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 100,
		recu.intValue());
	System.out.println("fini");
    }

    @Test
    public void testMultiSupplierMultiConsumer() throws InterruptedException,
	    EventClientException, ChannelAlreadyExistsException,
	    TopicNotFoundException, ChannelNotFoundException,
	    NotConnectedException, MaximalConnectionReachedException,
	    AlreadyConnectedException {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	for (int i = 0; i < 10; i++) {
	    MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class,
		    500, (String[]) null);
	    s2.go();
	}
	// ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	for (int i = 0; i < 10; i++) {
	    Thread t = new Thread(i + "") {
		public void run() {
		    try {
			EventClient ec = EventClient.init(null);
			ChannelAdmin channelAdmin = ec
				.resolveChannelByTopic("MEZZO");
			ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier();
			supplierProxy.connect();

			for (int i = 0; i < 10; i++) {
			    Header header = new Header(123, 1, Calendar
				    .getInstance().getTimeInMillis(), 10120);
			    Body body = new Body("Test_EVENT_" + i);
			    Event evt = new Event(header, body);
			    supplierProxy.push(evt);
			    System.out.println("envoye " + evt);
			    Thread.sleep(100);
			}
		    } catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (EventClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (TopicNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		};
	    };
	    t.start();
	}
	Thread.sleep(5000);
	esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 1000,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - CI14 - US165 - Cycle de vie des Event.
     * 
     * Teste la priorite : si plusieur event dans la Queue d'un consummer, ils
     * doivent lui être délivré dans l'ordre inverse du niveau de priorité.
     * 
     * Teste la duré de vie : seul ne sont envoyé au consumer les event dont 
     * (date synchronisee de creation + TTL) >= (date synchronisée courante).
     * 
     * @throws InterruptedException
     * @throws EventClientException
     * @throws ChannelAlreadyExistsException
     * @throws TopicNotFoundException
     * @throws ChannelNotFoundException
     * @throws NotConnectedException
     * @throws MaximalConnectionReachedException
     * @throws AlreadyConnectedException
     */
    @Test
    public void testCI14_CycleDeVieDesMessages() throws InterruptedException,
	    EventClientException, ChannelAlreadyExistsException,
	    TopicNotFoundException, ChannelNotFoundException,
	    NotConnectedException, MaximalConnectionReachedException,
	    AlreadyConnectedException {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer qui attendra 3000 ms entre le subscribe et le connnect de
	// son callback
	MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class, 2000,
		"4000");
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");

	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier();
	supplierProxy.connect();

	// envoie de 12 messages avec les priorités suivantes 4 2 5 3 1 4 2 4 5
	// 1 5 5
	// qui expirent au bout de 10 seconde pour les 2 premiers et 1s pour les
	// 2 derniers
	int[] inputPriorities = { 4, 2, 5, 3, 1, 4, 2, 4, 5, 1, 5, 5 };
	long[] inputTimeToLive = { 10000, 10000, 10000, 10000, 10000, 10000,
		10000, 10000, 10000, 10000, 1000, 1000 };
	// on s'attend à n'en recevoir que 10
	int[] expectedPriorities = { 5, 5, 4, 4, 4, 3, 2, 2, 1, 1 };
	for (int i = 0; i < 12; i++) {
	    Header header = new Header(i, inputPriorities[i], Calendar
		    .getInstance().getTimeInMillis(), inputTimeToLive[i]);
	    Body body = new Body("Test_EVENT_" + i);
	    Event evt = new Event(header, body);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	// envoie de 2 message
	// On attend que le consumer s'active cf les 4000 en paremetre de son
	// main donc 5000 ici.
	Thread.sleep(5000);
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	// lecture de la liste reçu
	int[] receivedPriorities = new int[10];
	;
	int j = 0;
	for (Event event : messagesRecu) {
	    receivedPriorities[j++] = event.header.priority;
	}
	// comparaison
	assertArrayEquals("Ordre des priorites", expectedPriorities,
		receivedPriorities);
	esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	System.out.println("fini");
    }

    @Test
    public void testUC07_Nominal_CreerUnEventChannel()
	    throws InterruptedException, EventClientException,
	    ChannelAlreadyExistsException, TopicNotFoundException,
	    ChannelNotFoundException, NotConnectedException,
	    MaximalConnectionReachedException, AlreadyConnectedException {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// check by id
	ChannelAdmin ca = esca.getChannel(idChannel);
	assertNotNull("getChannel returned null", ca);
	// check by CosNaming
	ChannelAdmin ca2 = ec.resolveChannelByTopic("MEZZO");
	assertNotNull("resolveChannelByTopic returned null", ca2);
	esca.destroyChannel(idChannel);
    }

    @Test
    public void testUC07_Alt1_SupprimerUnChannel() throws InterruptedException,
	    ChannelAlreadyExistsException, TopicNotFoundException,
	    NotConnectedException, MaximalConnectionReachedException,
	    AlreadyConnectedException, EventClientException,
	    ChannelNotFoundException {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// drop it
	esca.destroyChannel(idChannel);

	ChannelAdmin ca2;
	try {
	    // check by id
	    ca2 = esca.getChannel(idChannel);
	    fail("Channel still reachable by id !");
	} catch (ChannelNotFoundException e) {
	    // check by CosNaming
	    try {
		ChannelAdmin ca3 = ec.resolveChannelByTopic("MEZZO");
		fail("Channel still referenced in Name Service !");
	    } catch (TopicNotFoundException e1) {
		;// Ok
	    }
	}

    }
}
