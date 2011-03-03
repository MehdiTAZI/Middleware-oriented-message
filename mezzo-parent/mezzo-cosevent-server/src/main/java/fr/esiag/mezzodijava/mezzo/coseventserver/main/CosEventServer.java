package fr.esiag.mezzodijava.mezzo.coseventserver.main;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.exceptions.EventServerException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.CallbackTimeImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.EventServerChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.libclient.TimeClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TimeClientException;

/**
 * Class CosEventServer
 * 
 * Main class for the Mezzo di Java's COS Event Server
 * 
 * UC n°: US14,US15 (+US children) * Program argmuments:
 * 
 * -ORBInitRef NameService=corbaloc::127.0.0.1:1050/NameService
 * -Djacorb.home=C:\\mezzodev\\jacorb-2.3.1
 * -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 * -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 * 
 * VM argmuments: -Djava.endorsed.dirs=${env_var:JACORB_HOME}/lib
 * 
 * @author Mezzo-Team
 */

public class CosEventServer {

    /**
     * argument : eventServerName
     */
    private ORB orb;
    public static long delta = 0;

    /**
     * Constructor of a COS Event Server.
     * 
     * -read properties in eventserver.properties located at the calasspath
     * root.
     * 
     * -create the ORB instance.
     * 
     * -serve the Channel Admin alowing Consumers and Suppliers use the Channel.
     * 
     * -run The ThreadEvent to deliver Stored Event to the Callback of the PUSH
     * Consumer.
     * 
     * -run the ORB.
     * 
     * @param args
     *            Command min arguments
     * @throws InterruptedException
     * @throws TimeClientException
     * @throws EventServerException
     */
    public CosEventServer(String[] args) throws InterruptedException,
	    TimeClientException {

	String eventServerName = args[0];
	Properties props = new Properties();

	if (args != null) {
	    // channelName= args[0];
	    // eventServerName=args[1];

	}

	try {
	    props.load(this.getClass().getClassLoader()
		    .getResourceAsStream("eventserver.properties"));
	} catch (IOException e) {
	    // TODO log here

	}
	// for(String arg:args)
	// System.out.println(arg);

	orb = BFFactory.createOrb(args, props);

	/*
	 * orb = ORB.init(args, props); Channel channel = new
	 * Channel(channelName);
	 * 
	 * ChannelCtr channelCtr = new ChannelCtr(channel); ChannelAdminCtr
	 * channelAdminCtr = new ChannelAdminCtr(orb, channelCtr);
	 * ChannelAdminImpl channelAdminImpl = new ChannelAdminImpl(
	 * channelAdminCtr);
	 */
	// ChannelAdminImpl channelAdminImpl = BFFactory.initiateChannel(
	// channelName, 10);

	// ChannelPublisher publisher=new ChannelPublisher();
	// publisher.publish(channelAdminImpl, orb);
	// ChannelPublisher.publish(channelAdminImpl, orb);
	EventServerChannelAdminImpl eventServerChannelAdmin = new EventServerChannelAdminImpl(
		eventServerName);

	// ThreadEvent th = new ThreadEvent(channelName);
	// Thread thread = new Thread(th);
	// thread.start();

	try {
	    POA poa = POAHelper.narrow(orb
		    .resolve_initial_references("RootPOA"));
	    poa.the_POAManager().activate();
	    NamingContextExt nc = NamingContextExtHelper.narrow(orb
		    .resolve_initial_references("NameService"));

	    // nc.rebind(nc.to_name(channelName), poa.servant_to_reference(new
	    // ChannelAdminPOATie(channelAdminImpl)));
	    nc.rebind(nc.to_name(eventServerName), poa
		    .servant_to_reference(new EventServerChannelAdminPOATie(
			    eventServerChannelAdmin)));

	    // Subscribe to COSTime
	    // TODO Externalize this :
	    String cosTimeName = "MEZZO-TIME";
	    System.out.println("Mezzo COS Event Server \"" + eventServerName
		    + "\" is subscribing to COS Time " + cosTimeName);
	    TimeClient.init(null).subscribeToTimeService(cosTimeName,
		    new CallbackTimeImpl());

	    System.out.println("Mezzo COS Event Server \"" + eventServerName
		    + "\" is running...");

	    orb.run();

	} catch (InvalidName e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NotFound e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (CannotProceed e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ServantNotActive e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (WrongPolicy e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (AdapterInactive e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws InterruptedException,
	    TimeClientException {
	new CosEventServer(args);
    }

    public static long getDelta() {
	return delta;
    }

    public static void setDelta(long delta) {
	CosEventServer.delta = delta;
    }

}
