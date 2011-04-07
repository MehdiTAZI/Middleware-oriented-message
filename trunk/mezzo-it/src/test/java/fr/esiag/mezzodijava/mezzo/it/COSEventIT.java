package fr.esiag.mezzodijava.mezzo.it;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplierOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
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
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;
import fr.esiag.mezzodijava.mezzo.costime.TimeService;
import fr.esiag.mezzodijava.mezzo.costimeserver.main.CosTimeServer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;
import fr.esiag.mezzodijava.mezzo.libclient.TimeClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

/**
 * Mezzo Test Suite.
 * 
 * Launch first a name service Launch test with vm args (eclipse) :
 * -Djava.endorsed.dirs=${env_var:JACORB_HOME}/lib
 * 
 * Launch test with vm args (unix) : -Djava.endorsed.dirs=$JACORB_HOME/lib
 * 
 * Launch test with vm args (windows) : -Djava.endorsed.dirs=%JACORB_HOME%/lib
 * 
 * @author Mezzo-Team
 * 
 */
public class COSEventIT {

    public static String EVENT_SERVER_NAME;
    
    public static String TIME_SERVER_NAME;
    
 // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    } 
    
    @BeforeClass
    public static void beforeClass() throws InterruptedException {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// PropertyConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.properties"));
	// le time serveur
	
	TIME_SERVER_NAME = System.getProperty("mezzoit.timeservename");
	if (TIME_SERVER_NAME==null){
	    TIME_SERVER_NAME="MEZZO-COSTIME";
	    MainServerLauncher sl = new MainServerLauncher(CosTimeServer.class,
			1000, TIME_SERVER_NAME, "1000");
		sl.go();
	}
	EVENT_SERVER_NAME = System.getProperty("mezzoit.eventservename");
	if (EVENT_SERVER_NAME==null){
	    EVENT_SERVER_NAME="MEZZO-COSEVENT";
	    deleteDir(new File(System.getProperty("user.home")+System.getProperty("file.separator")+"dbcosevent"+EVENT_SERVER_NAME));
	    MainServerLauncher s2 = new MainServerLauncher(CosEventServer.class,
			5000, EVENT_SERVER_NAME);
		s2.go();
	}
	
    }

    public static Integer recu = 0;
    
    public static Integer envoye = 0;

    public static List<Event> messagesRecu = Collections
	    .synchronizedList(new ArrayList<Event>());

    public static class CallBackConsumerImpl implements
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

    public static long date;

    public static class CallBackTimeImpl implements SynchronizableOperations {
    	@Override
    	public long date() {
    		// TODO Auto-generated method stub
    		return 0;
    	}

		@Override
		public void date(long arg) {
		    COSEventIT.date = arg;
		}
    }

    public static class CallBackConsumerImpl2 implements
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
    
    public static class CallBackSupplierImpl implements
    CallbackSupplierOperations {
	   
	   List<Event> messages = Collections
	    .synchronizedList(new ArrayList<Event>());
	   
	   int nb;

	   public CallBackSupplierImpl() {
		   nb = 0;
		   for (int i = 0; i < 10; i++) {
			   Event e = EventFactory.createEventString(1, 10120, "Test_EVENT"+ i);
			   messages.add(e);
		   }
	   }
	   
		@Override
		public Event ask(BooleanHolder hasEvent)
				throws SupplierNotFoundException {
			
			nb++;
			
    		if (nb==11){
    			System.out.println("plus d events");
    			hasEvent.value=false;
    			Any a = BFFactory.getOrb().create_any();
				Header h = new Header();
				Body b = new Body();
				b.content=a;
				return new Event(h,b);
    		}else{
    			synchronized (envoye) {
    				envoye++;
    			}
    			System.out.println("envoi " + envoye + ",nb=" + nb);
    			hasEvent.value=true;
    			Event e = messages.get(nb-1);
    			return e;
    		}
		}
   }

    /**
     * Ce server de consummer abonne et connecte une CallBackConsumerImpl sur le
     * canal MEZZO.
     */
    private static class ConsumerServer1 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    EventClient ec = EventClient.init(null);
	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	    String idcomponent = Thread.currentThread().getName();
	    ProxyForPushConsumer consumerProxy = channelAdmin
		    .getProxyForPushConsumer(idcomponent);
	    CallBackConsumerImpl callbackImpl = new CallBackConsumerImpl();
	    CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
	    consumerProxy.subscribe();
	    if ((args != null) && (args.length >= 1)) {
		Thread.sleep(new Long(args[0]).longValue());
	    }
	    consumerProxy.connect(cbc);
	    ec.getOrb().run();
	    System.out.println("ALL DONE");
	}
    }

    /**
     * Ce server de consummer deconnecte apres 1s et se reconnecte apres 1s.
     * Pour le test
     */
    private static class ConsumerServer2 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    EventClient ec = EventClient.init(null);
	    Thread to = new Thread(new ThreadOrb());
	    to.setDaemon(true);
	    to.start();
	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	    String idcomponent = "mezzo";
	    ProxyForPushConsumer consumerProxy = channelAdmin
		    .getProxyForPushConsumer(idcomponent);
	    CallBackConsumerImpl callbackImpl = new CallBackConsumerImpl();
	    CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
	    consumerProxy.subscribe();
	    if ((args != null) && (args.length >= 1)) {
		Thread.sleep(new Long(args[0]).longValue());
	    }
	    consumerProxy.connect(cbc);
	    Thread.sleep(1000);
	    consumerProxy.disconnect();
	    System.out.println("***** " + recu);
	    Thread.sleep(1000);
	    consumerProxy.connect(cbc);

	    System.out.println("ALL DONE");
	}
    }

    /**
     * Ce server de consummer deconnecte apres 1s et se reconnecte apres 1s.
     * Pour le test
     */
    private static class TimeConsumerServer {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    TimeClient tc = TimeClient.init(null);
	    Thread to = new Thread(new ThreadOrb());
	    to.setDaemon(true);
	    to.start();
	    TimeService ts = tc.resolveTimeService(TIME_SERVER_NAME);
	    Synchronizable cc = tc.serveCallbackTime(new CallBackTimeImpl());
	    ts.subscribe(cc);
	    if ((args != null) && (args.length >= 1)) {
		Thread.sleep(new Long(args[0]).longValue());
	    }
	    ts.unsubscribe(cc);
	    System.out.println("ALL DONE");
	}
    }

    /**
     * Ce server de consummer destroy l'orb pendant un certain temps rendant les
     * consumers innaccessibles et le remet en run. Pour le test
     */
    private static class ConsumerServer3 {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    EventClient ec = EventClient.init(null);
	    Thread to = new Thread(new ThreadOrb());
	    to.setDaemon(true);
	    to.start();
	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	    String idcomponent = "mezzo";
	    ProxyForPushConsumer consumerProxy = channelAdmin
		    .getProxyForPushConsumer(idcomponent);
	    CallBackConsumerImpl callbackImpl = new CallBackConsumerImpl();
	    CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
	    consumerProxy.subscribe();
	    if ((args != null) && (args.length >= 1)) {
		Thread.sleep(new Long(args[0]).longValue());
	    }
	    consumerProxy.connect(cbc);
	    Thread.sleep(1000);
	    ec.getOrb().destroy();
	    Thread.sleep(1000);
	    ec.getOrb().run();

	    System.out.println("ALL DONE");
	}
    }

    private static class ThreadOrb implements Runnable {

	@Override
	public void run() {
	    try {
		EventClient.init(null).getOrb().run();
	    } catch (EventClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
    
    private static class SupplierPullServer {
    	@SuppressWarnings("unused")
    	public static void main(String[] args) throws Exception {
    	    EventClient ec = EventClient.init(null);
    	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
    	    String idcomponent = Thread.currentThread().getName();
    	    ProxyForPullSupplier supplierProxy = channelAdmin
    		    .getProxyForPullSupplier(idcomponent);
    	    CallBackSupplierImpl callbackImpl = new CallBackSupplierImpl();
    	    CallbackSupplier cbs = ec.serveCallbackSupplier(callbackImpl);
    	    if ((args != null) && (args.length >= 1)) {
    		Thread.sleep(new Long(args[0]).longValue());
    	    }
    	    supplierProxy.connect(cbs);
    	    ec.getOrb().run();
    	    System.out.println("ALL DONE");
    	}
    }
    
    private static class ConsumerPullServer {
    	@SuppressWarnings("unused")
    	public static void main(String[] args) throws Exception {
    	    EventClient ec = EventClient.init(null);
    	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
    	    String idcomponent = Thread.currentThread().getName();
    	    ProxyForPullConsumer consumerProxy = channelAdmin
			.getProxyForPullConsumer(idcomponent);
    	    if ((args != null) && (args.length >= 1)) {
    	    	Thread.sleep(new Long(args[0]).longValue());
    	    }
    	    consumerProxy.connect();
    	    Thread.sleep(15000);
    	    // begin of pull consumer 
    	    Event ev;
    	    BooleanHolder hasEvent = new BooleanHolder(true);
    	    while(hasEvent.value)
    	    {
    	    	System.out.println("pull consumer");
    	    	ev = consumerProxy.pull(hasEvent);
    	    	System.out.println(hasEvent.value);
    	    	if (hasEvent.value){
    	    		System.out.println("add_event");
    	    		messagesRecu.add(ev);
    	    	}
    	    }
    	    ec.getOrb().run();
    	    System.out.println("ALL DONE");
    	      
    		}
        }

    EventServerChannelAdmin esca;
    long idChannel;

    @Before
    public void beforeTest() throws InterruptedException, EventClientException {
	// mise a zero du compteur de message
	recu = 0;
	// mise blanc de la liste des messages reçus
	messagesRecu = Collections.synchronizedList(new ArrayList<Event>());

	esca = EventClient
		.init(null)
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	try{
	    esca.destroyChannel(1);
	}catch(Exception e){
	    ;
	}
    }

    @After
    public void afterTest() {
	try {
	    System.out.println(esca + "after = " + idChannel);
	    EventClient.shutdown();
	    EventClient
		    .init(null)
		    .resolveEventServerChannelAdminByEventServerName(
			    EVENT_SERVER_NAME).destroyChannel(idChannel);
	} catch (Exception e) {
	    ;
	}

    }

    @Test
    public void testUC0203_Nominal_UniqueSupplier() throws Exception {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	System.out.println("after = " + idChannel);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "mezzo";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();

	for (int i = 0; i < 10; i++) {
	    Event evt = EventFactory.createEventString(1, 10120, "Test_EVENT"
		    + i);

	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	System.out.println("fini");
    }

    @Test
    public void testUC0203_Nominal_MultiSupplier() throws Exception {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	//EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, (String[]) null);
	s2.go();
	// ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	for (int i = 0; i < 10; i++) {
	    Thread t = new Thread(i + "") {
		public void run() {
		    try {
			EventClient ec = EventClient.init(null);
			ChannelAdmin channelAdmin = ec
				.resolveChannelByTopic("MEZZO");
			String idcomponent = Thread.currentThread().getName();
			ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier(idcomponent);
			supplierProxy.connect();

			for (int i = 0; i < 10; i++) {
			    Event evt = EventFactory.createEventString(1,
				    10120, "Test_EVENT" + i);
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
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 100,
		recu.intValue());
	System.out.println("fini");
    }

    @Test
    public void testUC0203_Nominal_MultiSupplierMultiConsumer()
	    throws Exception {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	//EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	for (int i = 0; i < 10; i++) {
	    MainServerLauncher s2 = new MainServerLauncher(
		    ConsumerServer1.class, 500, (String[]) null);
	    s2.go();
	}
	// ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	for (int i = 0; i < 10; i++) {
	    Thread t = new Thread("supplier_" + i) {
		public void run() {
		    try {
			EventClient ec = EventClient.init(null);
			ChannelAdmin channelAdmin = ec
				.resolveChannelByTopic("MEZZO");
			String idcomponent = Thread.currentThread().getName();
			ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier(idcomponent);
			supplierProxy.connect();

			for (int j = 0; j < 10; j++) {
			    Event evt = EventFactory.createEventString(1,
				    10120, "Test_EVENT"
					    + Thread.currentThread().getName()
					    + "_" + j);
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
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 1000,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US140 - Alt1 : Reconnexion du Consummer.
     * 
     * Un cosummer se connecte, reçoie des event, se déconnecte, se reconnecte
     * et reçoie des event.
     * 
     */
    @Test
    public void testUC02_Alt1_ReconnexionDuConsumer() throws Exception {
	EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(
		COSEventIT.ConsumerServer2.class, 500, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "mezzo";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();

	for (int i = 0; i < 50; i++) {
	    Event evt = EventFactory.createEventString(1, 10120, "Test_EVENT"
		    + i);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 50,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US112 - Exc1 : LE Channel n'est pas toruve avec
     * son topic.
     * 
     * Un server de consumer demande un Channel au Name Service.
     * 
     */
    @Test
    public void testUC02_Exc1_ChannelNonTrouve() throws Exception {
	EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	try {
	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO_CRAP");
	    fail("Should have thrown TopicNotFoundException !");
	} catch (TopicNotFoundException e) {
	    ;// OK nothing to do.
	}

	Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US113 - Exc2 : Le Consumer n'arrive pas à se
     * connecter avec sa reference.
     * 
     * Un server de consumer demande un Channel au Name Service et essaye de s'y
     * connecter.
     * 
     */
    @Test
    public void testUC02_Exc2_CannotConnect() throws Exception {
	EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	System.out.println(idChannel);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");

	// destruction anticipee du Channel
	System.out.println("coucou");
	esca.destroyChannel(idChannel);
	System.out.println("coucou");
	String idcomponent = "mezzo";

	try {
	    ProxyForPushConsumer consumerProxy = channelAdmin
		    .getProxyForPushConsumer(idcomponent);
	    consumerProxy.subscribe();
	    consumerProxy.connect(ec
		    .serveCallbackConsumer(new CallBackConsumerImpl()));
	    fail("Should have exit");
	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US114 - Exc4 : Le Channel a atteind le nombre
     * maximal de connexion.
     * 
     * Un Channel de 2 connexion est créé. Un server de consumer demande un
     * Channel au Name Service, s'y connecter. Un dexuième essaye. Gestion de
     * l'exception
     * 
     */
    @Test
    public void testUC02_Exc3_MaximalConnexionReached() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 1);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idconsumer1 = "consumer1";
	String idconsumer2 = "consumer2";
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer(idconsumer1);
	// premiere connexion ok
	consumerProxy.subscribe();
	consumerProxy.connect(ec
		.serveCallbackConsumer(new CallBackConsumerImpl()));

	ProxyForPushConsumer consumerProxy2 = channelAdmin
		.getProxyForPushConsumer(idconsumer2);
	consumerProxy2.subscribe();

	try {
	    consumerProxy2.connect(ec
		    .serveCallbackConsumer(new CallBackConsumerImpl()));
	    fail("No MaximalConnectionReachedException thrown!");
	} catch (MaximalConnectionReachedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US115 - Exc4 : Le consumer est inaccessible par
     * l'event server.
     * 
     * CE TEST N'EST PAS ENCORE BON
     */
    @Test
    public void testUC02_Exc4_ConsumerInaccessible() throws Exception {
	fail("Not implemented test");
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	System.out.println(esca + " t4 " + idChannel);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(
		COSEventIT.ConsumerServer3.class, 500, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();

	for (int i = 0; i < 50; i++) {
	    Event evt = EventFactory.createEventString(1, 10120, "Test_EVENT"
		    + i);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 50,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US128 - Exc5 : Le Consumer se connecte mais est
     * deja connecte.
     * 
     * Un server de consumer demande un Channel au Name Service, y souscrit et
     * veut s'y connecter 2 fois.
     * 
     */
    @Test
    public void testUC02_Exc5_AlreadyConnected() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer(idcomponent);
	consumerProxy.subscribe();
	consumerProxy.connect(ec
		.serveCallbackConsumer(new CallBackConsumerImpl()));
	try {
	    consumerProxy.connect(ec
		    .serveCallbackConsumer(new CallBackConsumerImpl()));
	    fail("Expected AlreadyConnectedException !");
	} catch (AlreadyConnectedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US137 - Exc6 : Le Consumer se déconnecte mais
     * déjà déconnecté.
     * 
     * Un server de consumer demande un Channel au Name Service, y souscrit et
     * s'y connecte, s'y déconnecte 2 fois.
     */
    @Test
    public void testUC02_Exc6_NotConnected() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer(idcomponent);
	consumerProxy.subscribe();
	consumerProxy.connect(ec
		.serveCallbackConsumer(new CallBackConsumerImpl()));
	consumerProxy.disconnect();
	try {
	    consumerProxy.disconnect();
	    fail("Expected NotConnectedException !");
	} catch (NotConnectedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US138 - Exc7 : Le Consumer s’enregistre mais
     * déjà enregistré.
     * 
     * Un server de consumer demande un Channel au Name Service, y souscrit 2
     * fois.
     */
    @Test
    public void testUC02_Exc7_AlreadyRegistered() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer(idcomponent);
	consumerProxy.subscribe();
	consumerProxy.connect(ec
		.serveCallbackConsumer(new CallBackConsumerImpl()));
	try {
	    consumerProxy.subscribe();
	    fail("Expected AlreadyRegisteredException !");
	} catch (AlreadyRegisteredException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC02 - US139 - Exc8 : Le Consumer se désinscrit mais
     * il n’est pas inscrit.
     * 
     * Un server de consumer demande un Channel au Name Service, y souscrit puis
     * s'en desinscrit 2 fois.
     */
    @Test
    public void testUC02_Exc8_NotRegistered() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushConsumer consumerProxy = channelAdmin
		.getProxyForPushConsumer(idcomponent);
	consumerProxy.subscribe();
	consumerProxy.connect(ec
		.serveCallbackConsumer(new CallBackConsumerImpl()));
	consumerProxy.disconnect();
	consumerProxy.unsubscribe();
	try {
	    consumerProxy.unsubscribe();
	    fail("Expected NotRegisteredException !");
	} catch (NotRegisteredException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US129 - Alt1 : Reconnexion du Supplier.
     * 
     * Un supplier se connecte , envoie des event, se déconnecte, se reconnecte
     * et envoie des event.
     * 
     */
    @Test
    public void testUC03_Alt1_ReconnexionDuSupplier() throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();

	// on envoie 5 messages
	for (int i = 0; i < 5; i++) {
	    Event evt = EventFactory.createEventString(1, 10120, "Test_EVENT"
		    + i);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	// deconnexion
	supplierProxy.disconnect();
	Thread.sleep(500);
	// reconnection
	supplierProxy.connect();
	// on envoie 5 messages
	for (int i = 5; i < 10; i++) {
	    Header header = new Header(123, 1, Calendar.getInstance()
		    .getTimeInMillis(), 10120);

	    ORB orb = ORB.init();
	    Any any = orb.create_any();
	    any.insert_string("Test_EVENT" + i);
	    Body body = new Body(any, "String");

	    Event evt = new Event(header, body);
	    supplierProxy.push(evt);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US129 - Exc1 : Channel non trouve par son
     * Topic.
     * 
     * Un supplier se connecte , recherche un canal avec un mauvais topic et
     * gere l'exception.
     * 
     */
    @Test
    public void testUC03_Exc1_ChannelNonTrouve() throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	try {
	    ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO_CRAP");
	    fail("Should have thrown TopicNotFoundException !");
	} catch (TopicNotFoundException e) {
	    ;// OK nothing to do.
	}

	Thread.sleep(2000);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US131 - Exc2 : LE Supplier n'arrive pas à se
     * connecter avec la reference.
     * 
     * Un supplier se connecte , recherche un canal, et tente de s'y connecter
     */
    @Test
    public void testUC03_Exc2_ImpossibleDeSeConnecterAuChannel()
	    throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	// destruction anticipee du Channel
	esca.destroyChannel(idChannel);
	String idcomponent = "component";
	try {
	    ProxyForPushSupplier supplierProxy = channelAdmin
		    .getProxyForPushSupplier(idcomponent);
	    supplierProxy.connect();
	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	// esca.destroyChannel(idChannel);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US133 - Exc3 : Le Channel a atteind le nombre
     * maximal de connexion.
     * 
     * Un canal est créé avec une limite de une connexion. Un supplier se
     * connecte , puis un deuxième tente de s'y connecter.
     * 
     * @throws MaximalConnectionReachedException
     */
    @Test
    public void testUC03_Exc3_MaximalConnectionReached() throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 1);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	// premiere connexion ok
	supplierProxy.connect();

	try {
	    ProxyForPushSupplier supplierProxy2 = channelAdmin
		    .getProxyForPushSupplier(idcomponent);
	    supplierProxy2.connect();
	    fail("No MaximalConnectionReachedException raided !");
	} catch (MaximalConnectionReachedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US132 - Exc4 : Le Supplier se connecte mais est
     * deja connecte.
     * 
     * Un supplier recupere un proxy, se connecte , puis tente de s'y connecte
     * une deuxieme fois. Gestion de l'exception.
     * 
     * @throws MaximalConnectionReachedException
     */
    @Test
    public void testUC03_Exc4_SupplierDejaConnecte() throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 4);
	Thread.sleep(1000);
	// le consumer ici present
	// MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
	// 2000,
	// (String[]) null);
	// s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	// premiere connexion ok
	supplierProxy.connect();

	try {
	    supplierProxy.connect();
	    fail("No AlreadyConnectedException raised !");
	} catch (AlreadyConnectedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC03 - US126 - Exc5 : Le Supplier veut push un Event
     * ou se deconnecter mais n'est pas connecte.
     * 
     * Un supplier recupere un proxy, tente de push un event et tente de se
     * disconnect. Gestion de l'exception.
     */
    @Test
    public void testUC03_Exc5_SupplierPasConnecte() throws Exception {

	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 4);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	// connection, envoie d'event, deconnection
	supplierProxy.connect();
	ORB orb = ORB.init();
	Any any = orb.create_any();
	any.insert_string("contenu");

	supplierProxy.push(new Event(new Header(232, 1, (new Date()).getTime(),
		1000), new Body(any, "String")));
	supplierProxy.disconnect();
	// proxy recupere mais pas de connexion
	// tentative de push

	try {
	    supplierProxy.push(new Event(new Header(232, 1, (new Date())

	    .getTime(), 1000), new Body(any, "String")));
	    fail("push() didn't rose NotConnectedExcepetion !");
	} catch (NotConnectedException e) {
	    ; // ok
	}

	try {
	    supplierProxy.disconnect();
	    fail("disconnect() didn't rose NotConnectedExcepetion !");
	} catch (NotConnectedException e) {
	    ; // ok
	}
	// Thread.sleep(2000);
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC06 - US176 - Nominal : S’abonner au CosTime.
     * 
     * LE time service envoie l'heure toute les seconde. Connexion d'un
     * consommateur de temps (synchronizable) et vérification que l'heure est
     * reçu.
     */
    @Test
    public void testUC06_Nominal_SubscribeToTimeService() throws Exception {
	// TimeConsumer
	MainServerLauncher s = new MainServerLauncher(
		COSEventIT.TimeConsumerServer.class, 500, "11000");
	s.go();
	// boucle qui vérifi que la variable temps est bien mise à jour.
	long prev = date;
	for (int i = 0; i < 10; i++) {
	    Thread.sleep(1010);
	    Assert.assertTrue("reception ok toutes les secondes ",
		    date > prev + 1000);
	}

    }

    /**
     * Test Automatique - UC06 - US183 - Exc1 : Le callback est deja enregistré.
     * 
     * Connexion d'un consommateur de temps (synchronizable) et tentative de
     * connexion d'un deuxième.
     */
    @Test
    public void testUC06_Exc1_AlreadyRegistered() throws Exception {
	TimeClient tc = TimeClient.init(null);
	TimeService ts = tc.resolveTimeService(TIME_SERVER_NAME);
	Synchronizable s = tc.serveCallbackTime(new CallBackTimeImpl());
	ts.subscribe(s);
	try {
	    ts.subscribe(s);
	    fail("expected AlreadyRegisteredException !");
	} catch (fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException e) {
	    ;// ok
	}

	System.out.println("ALL DONE");

    }

    /**
     * Test Automatique - UC06 - US182 - Exc2 : Le callback n'etait pas
     * enregistré.
     * 
     * Connexion d'un consommateur de temps (synchronizable), desabonnement et
     * tentative d'un deuxième désabonnement.
     */
    @Test
    public void testUC06_Exc2_NotRegistered() throws Exception {
	TimeClient tc = TimeClient.init(null);
	TimeService ts = tc.resolveTimeService(TIME_SERVER_NAME);
	Synchronizable s = tc.serveCallbackTime(new CallBackTimeImpl());
	ts.subscribe(s);
	ts.unsubscribe(s);
	try {
	    ts.unsubscribe(s);
	    fail("expected NotRegisteredException !");
	} catch (fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException e) {
	    ;// ok
	}

	System.out.println("ALL DONE");

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
     */
    @Test
    public void testCI14_CycleDeVieDesMessages() throws Exception {
	EventClient ec = EventClient.init(null);
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	// le consumer qui attendra 3000 ms entre le subscribe et le connnect de
	// son callback
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, "4000");
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();

	// envoie de 12 messages avec les priorités suivantes 4 2 5 3 1 4 2 4 5
	// 1 5 5
	// qui expirent au bout de 10 seconde pour les 2 premiers et 1s pour les
	// 2 derniers
	int[] inputPriorities = { 4, 2, 5, 3, 1, 4, 2, 4, 5, 1, 5, 5 };
	long[] inputTimeToLive = { 10000, 10000, 10000, 10000, 10000, 10000,
		10000, 10000, 10000, 10000, 300, 300 };
	// on s'attend à n'en recevoir que 10
	int[] expectedPriorities = { 5, 5, 4, 4, 4, 3, 2, 2, 1, 1 };
	for (int i = 0; i < 12; i++) {
	    Event evt = EventFactory.createEventString(inputPriorities[i],
		    inputTimeToLive[i], "Test_EVENT" + i);
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
	Assert.assertEquals("nombre d'event envoyes et recus", 10,
		recu.intValue());
	System.out.println("fini");
    }

    /**
     * Test Automatique - UC07 - US76 - Nominal : Création d’un Event Channel
     * sur un Event Server.
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions.
     * 
     */
    @Test
    public void testUC07_Nominal_CreerUnEventChannel() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// check by id
	ChannelAdmin ca = esca.getChannel(idChannel);
	assertNotNull("getChannel returned null", ca);
	// check by CosNaming
	ChannelAdmin ca2 = ec.resolveChannelByTopic("MEZZO");
	assertNotNull("resolveChannelByTopic returned null", ca2);
    }

    /**
     * Test Automatique - UC07 - US160 - Alt1 : Suppression d’un Event Channel
     * sur un Event Server.
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions, connecte 1 supplier. et supprime le canal. Test si :
     * 
     * - il n'est plus reference dans le name service
     * 
     * - son interface n'est plus publie
     * 
     * - les proxy eventuellement recuperes ne sont plus publies
     * 
     */
    @Test
    public void testUC07_Alt1_SupprimerUnChannel() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	long idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// on recupere un supplier dessus
	ChannelAdmin ca = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "component";
	// on recupere un proxy...
	ProxyForPushSupplier pps = ca.getProxyForPushSupplier(idcomponent);
	// drop it
	esca.destroyChannel(idChannel);

	try {
	    // check by id
	    ChannelAdmin ca2 = esca.getChannel(idChannel);
	    fail("ChannelAdmin still reachable by id !");
	} catch (ChannelNotFoundException e) {
	    ;// OK
	}
	// check by CosNaming
	try {
	    ChannelAdmin ca3 = ec.resolveChannelByTopic("MEZZO");
	    fail("ChannelAdmin still referenced in Name Service !");
	} catch (TopicNotFoundException e1) {
	    ;// Ok
	}

	// tentative de manipulation du channel admin
	try {
	    ca.getProxyForPushSupplier(idcomponent);
	    fail("ChannelAdmin still active ?");
	} catch (org.omg.CORBA.UserException e) {
	    fail("ChannelAdmin still active ?");
	    ;
	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
	    ; // ok
	}
	// tentative de manipulation du proxy
	try {
	    pps.connect();
	    fail("Proxy still active!");
	} catch (org.omg.CORBA.UserException e) {
	    fail("Proxy still active ?");
	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
	    ; // ok
	}
    }

    /**
     * Test Automatique - UC07 - US166 - Alt2 : Modification de la capacité du
     * channel sur un Event Server.
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions. Puis le montre à 3. Connecte 3 supplier dessus. Deconnecte 2
     * supplier et repasse à 1.
     * 
     */
    @Test
    public void testUC07_Alt2_ModifierCapacite() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// on recupere un supplier dessus
	ChannelAdmin ca = ec.resolveChannelByTopic("MEZZO");
	String idsupplier1 = "supplier1";
	String idsupplier2 = "supplier2";
	String idsupplier3 = "supplier3";
	String idconsumer1 = "consumer1";
	String idconsumer2 = "consumer2";
	String idconsumer3 = "consumer3";
	// 2 supplier
	ProxyForPushSupplier pps = ca.getProxyForPushSupplier(idsupplier1);
	pps.connect();
	ProxyForPushSupplier pps2 = ca.getProxyForPushSupplier(idsupplier2);
	pps2.connect();

	// 2 consumer
	ProxyForPushConsumer ppc = ca.getProxyForPushConsumer(idconsumer1);
	ppc.subscribe();
	ppc.connect(ec.serveCallbackConsumer(new CallBackConsumerImpl()));

	ProxyForPushConsumer ppc2 = ca.getProxyForPushConsumer(idconsumer2);
	ppc2.subscribe();
	ppc2.connect(ec.serveCallbackConsumer(new CallBackConsumerImpl()));

	// on augmente à 3
	esca.changeChannelCapacity(idChannel, 3);

	// 3 ieme supplier
	ProxyForPushSupplier pps3 = ca.getProxyForPushSupplier(idsupplier3);
	pps3.connect();

	// 3 ieme consumer
	ProxyForPushConsumer ppc3 = ca.getProxyForPushConsumer(idconsumer3);
	ppc3.subscribe();
	ppc3.connect(ec.serveCallbackConsumer(new CallBackConsumerImpl()));

	// disconnect de 2 supplier
	pps2.disconnect();
	pps3.disconnect();
	// disconnecte de 2 consumer
	ppc2.disconnect();
	ppc3.disconnect();

	// on reduit à 1
	esca.changeChannelCapacity(idChannel, 1);

    }

    /**
     * Test Automatique - UC07 - US2159 - Exc1 : Créer un channel dont le topic
     * existe déjà.
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions. Tente de creer un 2ieme canal MEZZO.
     * 
     */
    @Test
    public void testUC07_Exc1_ChannelAlreadyExists() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// check by id
	ChannelAdmin ca = esca.getChannel(idChannel);
	assertNotNull("getChannel returned null", ca);
	// check by CosNaming
	ChannelAdmin ca2 = ec.resolveChannelByTopic("MEZZO");
	assertNotNull("resolveChannelByTopic returned null", ca2);

	// 2 ieme tentative
	try {
	    idChannel = esca.createChannel("MEZZO", 2);
	    fail("expected ChannelAlreadyExistsException");
	} catch (ChannelAlreadyExistsException e) {
	    ; // ok
	}
    }

    /**
     * Test Automatique - UC07 - US2161 - Exc2 : A.ceder à un Channel qui
     * n’existe pas
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions. Essaye de supprimer une canal en donnant un faux id.
     * 
     */
    @Test
    public void testUC07_Exc2_ChannelNotFound() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);

	// on va supprimer un channel qui n'existe pas
	try {
	    esca.destroyChannel(99999999);
	    fail("expected ChannelNotFoundException");
	} catch (ChannelNotFoundException e) {
	    ; // ok
	}
    }

    /**
     * Test Automatique - UC07 - US167 - Exc3 : Reduire la capacité du channel
     * sur un Event Server.
     * 
     * Se connecte à un event serveur et crée un canal nommé MEZZO de capacité 2
     * connexions. D'abord on essaye de reduire a -1 et on s'attend a
     * CannotRedcuCapacityException. Puis on y met 2 supplier et 2 consummer.
     * Puis le réduit à 3. Gestion de l'exception. supplier et repasse à 1.
     * 
     */
    @Test
    public void testUC07_Exc3_CannotReduceCapacity() throws Exception {
	EventClient ec = EventClient.init(null);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 2);
	Thread.sleep(1000);
	// on recupere un supplier dessus
	ChannelAdmin ca = ec.resolveChannelByTopic("MEZZO");
	String idsupplier1 = "supplier1";
	String idsupplier2 = "supplier2";
	String idconsumer1 = "consumer1";
	String idconsumer2 = "consumer2";
	// test si on reduit a un nombre negatif
	try {
	    esca.changeChannelCapacity(idChannel, -1);
	    fail("expected CannotReduceCapacityException !");
	} catch (CannotReduceCapacityException e) {
	    ;// ok
	}

	// 2 supplier
	ProxyForPushSupplier pps = ca.getProxyForPushSupplier(idsupplier1);
	pps.connect();
	ProxyForPushSupplier pps2 = ca.getProxyForPushSupplier(idsupplier2);
	pps2.connect();

	// 2 consumer
	ProxyForPushConsumer ppc = ca.getProxyForPushConsumer(idconsumer1);
	ppc.subscribe();
	ppc.connect(ec.serveCallbackConsumer(new CallBackConsumerImpl()));

	ProxyForPushConsumer ppc2 = ca.getProxyForPushConsumer(idconsumer2);
	ppc2.subscribe();
	ppc2.connect(ec.serveCallbackConsumer(new CallBackConsumerImpl()));

	// on tente de reduire à 1
	try {
	    esca.changeChannelCapacity(idChannel, 1);
	    fail("expected CannotReduceCapacityException !");
	} catch (CannotReduceCapacityException e) {
	    ;// ok
	}
    }

    /**
     * Test Automatique - CI03 - US38 - Messages de tous types.
     * 
     * Crée un consumer sur le canal MEZZO qui peut recevoir des message de type
     * MyMessage Crée un supplier émettent 1 message de type MyMessage
     * 
     */
    @Test
    public void testCI03_MessageDeTousTypes() throws Exception {
	// DOMConfigurator.configure(COSEventIT.class.getClassLoader().getResource("log4j.xml"));
	// assume SLF4J is bound to logback in the current environment
	// LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	// print logback's internal status
	// StatusPrinter.print(lc);

	EventClient ec = EventClient.init(null);
	// EventServerChannelAdmin esca = ec
	// .resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
	idChannel = esca.createChannel("MEZZO", 20);
	System.out.println("after = " + idChannel);
	Thread.sleep(1000);
	// le consumer ici present
	MainServerLauncher s2 = new MainServerLauncher(ConsumerServer1.class,
		2000, (String[]) null);
	s2.go();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");
	String idcomponent = "mezzo";
	ProxyForPushSupplier supplierProxy = channelAdmin
		.getProxyForPushSupplier(idcomponent);
	supplierProxy.connect();
	List<MyMessage> listEnvoyes = new ArrayList<MyMessage>();
	for (int i = 0; i < 1; i++) {
	    MyMessage message = new MyMessage("message" + i);
	    Event evt = EventFactory.createEventObject(i, 10120, message,
		    message.getClass().getName());
	    supplierProxy.push(evt);
	    listEnvoyes.add(message);
	    System.out.println("envoye " + evt);
	    Thread.sleep(100);
	}
	Thread.sleep(5000);
	// esca.destroyChannel(idChannel);
	Assert.assertEquals("nombre d'event envoyes et recus", 1,
		recu.intValue());
	Iterator<MyMessage> it = listEnvoyes.iterator();
	for (Event event : messagesRecu) {
	    Assert.assertEquals("contenu du message", it.next().myString,
		    ((MyMessage) event.body.content.extract_Value()).myString);
	}
	System.out.println("fini");
    }
    
    @Test
    public void testUC01_Nominal_UniqueConsumerPull() throws Exception {
    	EventClient ec = EventClient.init(null);
    	EventServerChannelAdmin esca = ec
    		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
    	idChannel = esca.createChannel("MEZZO", 2);
    	Thread.sleep(1000);
    	
    	ChannelAdmin ca = ec.resolveChannelByTopic("MEZZO");
    	String idsupplier1 = "supplier1";
    	String idconsumer1 = "consumer1";

    	// supplier
    	ProxyForPushSupplier pps = ca.getProxyForPushSupplier(idsupplier1);
    	pps.connect();

    	// consumer
    	ProxyForPullConsumer ppc = ca.getProxyForPullConsumer(idconsumer1);
    	ppc.connect();
    	
    	for (int i = 0; i < 10; i++) {
    	    Event evt = EventFactory.createEventString(1, 10120, "Test_EVENT"
    		    + i);

    	    pps.push(evt);
    	    System.out.println("envoye " + evt);
    	    Thread.sleep(100);
    	}
    	Thread.sleep(2000);
	    Event ev;
	    BooleanHolder hasEvent = new BooleanHolder(true);
	    while(hasEvent.value)
	    {
		    ev = ppc.pull(hasEvent);
		    if (hasEvent.value){
		    	messagesRecu.add(ev);
		    }
	     }
    	
    	Assert.assertEquals("nombre d'event envoyes et recus", 10,
    			messagesRecu.size());
    		System.out.println("fini");
    } 	
    
    @Test
    public void testUC04_Nominal_UniqueSupplierPull()
	    throws Exception {
    	
    	EventClient ec = EventClient.init(null);
    	EventServerChannelAdmin esca = ec
    		.resolveEventServerChannelAdminByEventServerName(EVENT_SERVER_NAME);
    	idChannel = esca.createChannel("MEZZO", 2);
    	Thread.sleep(1000);
    	
    	ChannelAdmin ca = ec.resolveChannelByTopic("MEZZO");
    	String idconsumer1 = "consumer1";
    	
    	// consumer
    	ProxyForPullConsumer ppc = ca.getProxyForPullConsumer(idconsumer1);
    	ppc.connect();
    	
    	// supplier
    	MainServerLauncher s = new MainServerLauncher(SupplierPullServer.class,
    			1000, (String[]) null);
    		s.go();
    	Thread.sleep(10000);
    	
    	// begin of pull consumer 
	    Event ev;
	    BooleanHolder hasEvent = new BooleanHolder(true);
	    while(hasEvent.value)
	    {
		    ev = ppc.pull(hasEvent);
		    if (hasEvent.value){
		    	messagesRecu.add(ev);
		    }
	     }
    	Assert.assertEquals("nombre d'event envoyes et recus", 10,
    			messagesRecu.size());
    		System.out.println("fini");
    }
    
    @Test
    public void testUC0104_Nominal_MultiSupplierPullMultiConsumerPull()
	    throws Exception {
	idChannel = esca.createChannel("MEZZO", 20);
	Thread.sleep(1000);
	for (int i = 0; i < 10; i++) {
		MainServerLauncher s = new MainServerLauncher(
				ConsumerPullServer.class, 200, (String[]) null);
		s.go();
	}
	// les suppliers ici present
	for (int i = 0; i < 10; i++) {
	    MainServerLauncher s = new MainServerLauncher(
		    SupplierPullServer.class, 200, (String[]) null);
	    s.go();
	}
	Thread.sleep(15000);
	Assert.assertEquals("nombre d'event envoyes et recus", 1000,
		messagesRecu.size());
	System.out.println("fini");
    }
	
}