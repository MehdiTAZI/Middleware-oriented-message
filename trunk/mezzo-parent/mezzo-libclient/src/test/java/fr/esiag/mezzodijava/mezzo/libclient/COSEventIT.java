package fr.esiag.mezzodijava.mezzo.libclient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

import org.junit.Assert;
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

import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class COSEventIT {

    @BeforeClass
    public static void beforeClass() {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// PropertyConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.properties"));

    }

    public class MainServerLauncher implements Runnable {

	Method mainMethod;

	long delay;

	String[] args;

	public MainServerLauncher(Class<?> mainClass, long delay,
		String... args) {
	    this.delay = delay;
	    this.args = args;
	    try {

		// Create the array of Argument Types
		Class<?>[] argTypes = { String[].class };

		// Now find the method
		mainMethod = mainClass.getMethod("main", argTypes);
		// System.out.println(mainMethod);

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	public void go() throws InterruptedException {
	    Thread t = new Thread(this);
	    t.setDaemon(true);
	    t.start();
	    Thread.sleep(this.delay);
	}

	@Override
	public void run() {
	    // Create the actual argument array
	    Object passedArgv[] = { args };

	    // Now invoke the method.
	    try {
		mainMethod.invoke(null, passedArgv);
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}

    }

    public static Integer recu = 0;

    public static class callBackConsumerImpl implements
	    CallbackConsumerOperations {

	@Override
	public void receive(Event evt) throws ConsumerNotFoundException {
	    System.out.println("recu " + evt);
	    synchronized (recu) {
		recu++;
	    }

	}

    }

    public static void main(String[] args) throws ChannelNotFoundException,
	    AlreadyRegisteredException, EventClientException,
	    TopicNotFoundException, ChannelAlreadyExistsException,
	    NotRegisteredException, InterruptedException,
	    MaximalConnectionReachedException, AlreadyConnectedException,
	    NotConnectedException {
	EventClient ec = EventClient.init(null);
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer();
	callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
	CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
	consumerProxy.subscribe(cbc);
	consumerProxy.connect();
	ec.getOrb().run();
	System.out.println("ALL DONE");
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

	// le serveur
	MainServerLauncher sl = new MainServerLauncher(CosEventServer.class,
		2000, "MEZZO-SERVER");
	sl.go();

	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
	long idChannel =  esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class, 2000,
		(String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");

	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier();
	supplierProxy.connect();

	System.out.println("test3");
	recu = 0;
	for (int i = 0; i < 10; i++) {
	    Header header = new Header(123, 1, Calendar.getInstance()
		    .getTimeInMillis(), 10120);
	    Body body = new Body("Test_EVENT_" + i);
	    Event evt = new Event(header, body);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(10000);
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

	// le serveur
	MainServerLauncher sl = new MainServerLauncher(CosEventServer.class,
		2000, "MEZZO-SERVER");
	sl.go();

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
	recu = 0;
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

			System.out.println("test3");
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
	Thread.sleep(10000);
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

	// le serveur
	MainServerLauncher sl = new MainServerLauncher(CosEventServer.class,
		2000, "MEZZO-SERVER");
	sl.go();
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
	recu = 0;
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

			System.out.println("test3");
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
	Thread.sleep(10000);
	esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 1000,
		recu.intValue());
	System.out.println("fini");
    }
    //
    // @Test
    // public void testMultiSupplier() throws InterruptedException,
    // EventClientException, ChannelAlreadyExistsException {
    // recu = 0;
    // MainServerLauncher sl = new MainServerLauncher(CosEventServer.class,
    // 2000, "MEZZO-SERVER");
    // sl.go();
    // Thread.sleep(1000);
    // // le consumer ici present
    // MainServerLauncher s2 = new MainServerLauncher(COSEventIT.class, 2000,
    // (String[])null);
    // EventClient ec = EventClient.init(null);
    // EventServerChannelAdmin esca = ec
    // .resolveEventServerChannelAdminByEventServerName("MEZZO-SERVER");
    // esca.createChannel("MEZZO", 20);

    // System.out.println("test");
    // for (int i = 0; i < 10; i++) {
    // Thread t = new Thread(i + "") {
    // public void run() {
    // try {
    // System.out.println("test2");
    // EventClient ec = EventClient.init(null);
    // // orb = ec.getOrb();
    // ChannelAdmin channelAdmin = ec
    // .resolveChannelByTopic("MEZZO");
    //
    // ProxyForPushSupplier supplierProxy = channelAdmin
    // .getProxyForPushSupplier();
    // supplierProxy.connect();
    // System.out.println("test3");
    // for (int i = 0; i < 10; i++) {
    // Header header = new Header(123, 1, 01012011, 10120);
    // Body body = new Body("Test_EVENT_"+i);
    // Event evt = new Event(header, body);
    // supplierProxy.push(evt);
    // System.out.println("envoye " + evt);
    // Thread.sleep(100);
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // Assert.fail();
    // }
    // }
    //
    // };
    // t.setDaemon(false);
    // t.start();
    // }
    // Thread.sleep(10000);
    // Assert.assertEquals("nombre d'event envoyes et recus", 100,
    // recu.intValue());
    // System.out.println("fini");
    // }
}
