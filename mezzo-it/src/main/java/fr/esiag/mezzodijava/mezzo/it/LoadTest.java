package fr.esiag.mezzodijava.mezzo.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.BooleanHolder;

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplierOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;

public class LoadTest {

    // Push Supplier delay
    private static long psDelay = 100;

    private static String serverName;
    private static int maxChannel;
    private static int maxPushCon;
    private static int maxPushSup;
    private static int maxPullCon;
    private static int maxPullSup;
    private static int duration;

    private static EventServerChannelAdmin esca;

    private static Map<String, Long> channels = new HashMap<String, Long>();;

    private static Map<String, Long> pullSuppliedSent = new HashMap<String, Long>();

    private static Map<String, Long> pushSuppliedSent = new HashMap<String, Long>();

    private static Map<String, List<Long>> pushConsumptionDelays = new HashMap<String, List<Long>>();

    private static Map<String, List<Long>> pullConsumptionDelays = new HashMap<String, List<Long>>();

    public static boolean live = true;

    public static long connected = 0;
    
    public static long expectedConnected = 0;
    
    public static void main(String args[]) {
	if (args.length != 7) {
	    printUsage();
	    System.exit(0);
	}
	try {
	    serverName = args[0];
	    maxChannel = new Integer(args[1]);
	    maxPushSup = new Integer(args[2]);
	    maxPushCon = new Integer(args[3]);
	    maxPullSup = new Integer(args[4]);
	    maxPullCon = new Integer(args[5]);
	    duration = new Integer(args[6])*1000;
	} catch (Exception e) {
	    printUsage();
	}
	initServer();
	try {
	    initChannels();
	    
	    connected=0;
	    expectedConnected=(maxPushCon+maxPushSup+maxPullCon+maxPullSup)*maxChannel;
	    launchComponentServers();
	    //wait for connected
	    while(connected<expectedConnected){
		    Thread.sleep(10);
		}
	    System.out.println("Everybody ("+connected+") seem to be connected... Lets's rock !");
	    // duree du test ATTENTION TEMPS DE CONNECTION COMPRIS
	    try {
		Thread.sleep(duration);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    live = false;
	    // pour redescendre :
	    try {
		Thread.sleep(3000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    displayStats();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    removeChannels();
	}
    }
    
    public static void printUsage() {
	System.out.println("Mezzo COS Event LOAD TEST");
	System.out.println("Usage :");
	System.out
		.println("coseventload <server_name> <channel nb> <push_supplier_nb> <push_consumer_nb> <pull_supplier_nb> <pull_consumer_nb> <duration_in_second>");
    }

    public static void initServer() {
	esca = EventClient.init(null)
		.resolveEventServerChannelAdminByEventServerName(serverName);
    }

    public static void initChannels() throws ChannelAlreadyExistsException {
	for (int i = 0; i < maxChannel; i++) {
	    String topic = "CHANNEL_" + i;
	    channels.put(
		    topic,
		    esca.createChannel(topic, maxPushCon + maxPushSup
			    + maxPullCon + maxPullSup));
	}
    }

    public static void launchComponentServers() throws InterruptedException {
	for (String topic : channels.keySet()) {
	    for (int j = 0; j < maxPushCon; j++) {
		MainServerLauncher s = new MainServerLauncher(
			PushConsumerServer.class, 0, topic + "_PUSH-CONSUMER_"
				+ j, topic);
		s.go();
	    }
	    for (int j = 0; j < maxPullSup; j++) {
		MainServerLauncher s = new MainServerLauncher(
			PullSupplierServer.class, 0, topic + "_PULL-SUPPLIER_"
				+ j, topic);
		s.go();
	    }
	    for (int j = 0; j < maxPullCon; j++) {
		MainServerLauncher s = new MainServerLauncher(
			PullConsumerServer.class, 0, topic + "_PULL-CONSUMER_"
				+ j, topic);
		s.go();
	    }
	    for (int j = 0; j < maxPushSup; j++) {
		MainServerLauncher s = new MainServerLauncher(
			PushSupplierServer.class, 0, topic + "_PUSH-SUPPLIER_"
				+ j, topic);
		s.go();
	    }
	}
    }

    public static void displayStats() {
	long totPushSuppliedNb = 0;
	long totPullSuppliedNb = 0;
	long totPushConsumedNb = 0;
	long totPullConsumedNb = 0;
	double avgPushConsumedDelay = 0;
	double avgPullConsumedDelay = 0;
	for (Long nb : pushSuppliedSent.values()) {
	    totPushSuppliedNb += nb;
	}
	for (Long nb : pullSuppliedSent.values()) {
	    totPullSuppliedNb += nb;
	}
	long totPullConsumedDelay = 0;
	for (List<Long> list : pullConsumptionDelays.values()) {
	    totPullConsumedNb += list.size();
	    for (Long delay : list) {
		totPullConsumedDelay += delay;
	    }
	}
	avgPullConsumedDelay = (double) totPullConsumedDelay
		/ (double) totPullConsumedNb;
	long totPushConsumedDelay = 0;
	for (List<Long> list : pushConsumptionDelays.values()) {
	    totPushConsumedNb += list.size();
	    for (Long delay : list) {
		totPushConsumedDelay += delay;
	    }
	}
	avgPushConsumedDelay = (double) totPushConsumedDelay
		/ (double) totPushConsumedNb;

	System.out.println("Results :");
	System.out.println("------- :");
	System.out.println("Test summary : "+maxChannel+" channels and per channel :");
	System.out.println("  PUSH Suppliers : "+maxPushSup+"    PUSH Consumers : "+maxPushCon);
	System.out.println("  PULL Suppliers : "+maxPullSup+"    PULL Consumers : "+maxPullCon);
	System.out
		.println("Total Push Supplied Events (nb)                      : "
			+ totPushSuppliedNb);
	System.out
		.println("Total Pull Supplied Events (nb)                      : "
			+ totPullSuppliedNb);
	System.out
		.println("Total Push Consumed Events (nb)                      : "
			+ totPushConsumedNb);
	System.out
		.println("Average transport delay of Push Consumed Events (ms) : "
			+ avgPushConsumedDelay);
	System.out
		.println("Total Pull Consumed Events (nb)                      : "
			+ totPullConsumedNb);
	System.out
		.println("Average transport delay of Pull Consumed Events (ms) : "
			+ avgPullConsumedDelay);
    }

    public static void removeChannels() {
	for (Map.Entry<String, Long> e : channels.entrySet()) {
	    try {
		esca.destroyChannel(e.getValue());
	    } catch (ChannelNotFoundException e1) {
		System.err.println("Channel Not Found " + e.getKey());
	    }
	}
    }

    private static class PushSupplierServer {
	public static long sent = 0;

	public static void main(String[] args) throws Exception {
	    String name = args[0];
	    String topic = args[1];
	    try {
		Thread.currentThread().setName(name);
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic(topic);
		ProxyForPushSupplier supplierProxy = channelAdmin
			.getProxyForPushSupplier(name);
		supplierProxy.connect();
		connected++;
		System.out.println("connected push supplier n°" + name);
		while(connected<expectedConnected){
		    Thread.sleep(100);
		}
		while (live) {
		    Event evt = EventFactory.createEventString(1, 10120,
			    "Test_EVENT");
		    supplierProxy.push(evt);
		    sent++;
		    Thread.sleep(psDelay);
		}
		supplierProxy.disconnect();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    pushSuppliedSent.put(name, sent);
	    System.out.println("ALL DONE push supplier n°" + name);
	}
    }

    /**
     * Ce server de consummer abonne et connecte une CallBackConsumerImpl sur le
     * canal MEZZO.
     */
    private static class PushConsumerServer {

	private static List<Long> delays = new ArrayList<Long>();

	public static void main(String[] args) throws Exception {
	    String name = args[0];
	    String topic = args[1];
	    try {
		Thread.currentThread().setName(name);
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic(topic);
		ProxyForPushConsumer consumerProxy = channelAdmin
			.getProxyForPushConsumer(name);
		CallBackConsumerImpl callbackImpl = new CallBackConsumerImpl();
		CallbackConsumer cbc = ec.serveCallbackConsumer(callbackImpl);
		consumerProxy.subscribe();
		consumerProxy.connect(cbc);
		connected++;
		System.out.println("connected push consumer n°" + name);
		while (live) {
		    Thread.sleep(100);
		}		
		consumerProxy.disconnect();
		consumerProxy.unsubscribe();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    pushConsumptionDelays.put(name, delays);
	    System.out.println("ALL DONE push consumer n°" + name);
	}

	private static class CallBackConsumerImpl implements
		CallbackConsumerOperations {

	    @Override
	    public void receive(Event evt) throws ConsumerNotFoundException {
		delays.add(System.currentTimeMillis() - evt.header.creationdate);
	    }

	}
    }

    private static class PullSupplierServer {
	public static long sent = 0;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    String name = args[0];
	    String topic = args[1];
	    try {
		Thread.currentThread().setName(name);
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic(topic);
		ProxyForPullSupplier supplierProxy = channelAdmin
			.getProxyForPullSupplier(name);
		CallBackSupplierImpl callbackImpl = new CallBackSupplierImpl();
		CallbackSupplier cbs = ec.serveCallbackSupplier(callbackImpl);
		supplierProxy.connect(cbs);
		connected++;
		System.out.println("connected pull supplier n°" + name);
		while (live) {
		    Thread.sleep(100);
		}
		supplierProxy.disconnect();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    pullSuppliedSent.put(name, sent);
	    System.out.println("ALL DONE SUPPLIER pull supplier n°" + name);
	}

	private static class CallBackSupplierImpl implements
		CallbackSupplierOperations {

	    public CallBackSupplierImpl() {

	    }

	    @Override
	    public Event ask(BooleanHolder hasEvent)
		    throws SupplierNotFoundException {

		Event e = EventFactory
			.createEventString(1, 10120, "Test_EVENT");
		hasEvent.value = true;
		sent++;
		return e;
	    }
	}
    }

    private static class PullConsumerServer {
	public static List<Long> delays = new ArrayList<Long>();

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
	    String name = args[0];
	    String topic = args[1];
	    try {
		Thread.currentThread().setName(name);
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic(topic);
		ProxyForPullConsumer consumerProxy = channelAdmin
			.getProxyForPullConsumer(name);
		consumerProxy.connect();
		connected++;
		System.out.println("connected pull consumer n°" + name);
		while(connected<expectedConnected){
		    Thread.sleep(100);
		}
		// begin of pull consumer
		Event ev;
		BooleanHolder hasEvent = new BooleanHolder(true);
		while (live) {
		    ev = consumerProxy.pull(hasEvent);
		    if (hasEvent.value) {
			delays.add(System.currentTimeMillis()
				- ev.header.creationdate);
		    }
		}
		consumerProxy.disconnect();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    pullConsumptionDelays.put(name, delays);
	    System.out.println("ALL DONE pull consumer n°" + name);

	}
    }

}
