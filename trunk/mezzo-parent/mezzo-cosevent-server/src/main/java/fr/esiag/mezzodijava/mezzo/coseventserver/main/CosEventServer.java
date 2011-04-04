package fr.esiag.mezzodijava.mezzo.coseventserver.main;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDAO;
import fr.esiag.mezzodijava.mezzo.coseventserver.exceptions.EventServerException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.CallbackTimeImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.EventServerChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.publisher.ChannelPublisher;
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

    final static Logger log = LoggerFactory.getLogger(CosEventServer.class);

    /**
     * argument : eventServerName
     */
    private ORB orb;
    private static long delta = 0;

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
    /**
     * @param args
     * @throws InterruptedException
     * @throws TimeClientException
     * @throws EventServerException
     */
    public CosEventServer(String[] args) throws InterruptedException,
	    TimeClientException, EventServerException {

	String eventServerName = args[0];
	Properties props = new Properties();

//	if (args != null) {
//	    channelName= args[0];
//	     eventServerName=args[1];
//
//	}

	try {
	    props.load(this.getClass().getClassLoader()
		    .getResourceAsStream("eventserver.properties"));
	} catch (IOException e) {
	   log.error("Cannot load eventserver properties",e);
	   throw new EventServerException("Cannot load eventserver properties",e);

	}


	orb = BFFactory.createOrb(args, props);

	EventServer.getInstance().setServerName(eventServerName);
	
	EventServerChannelAdminImpl eventServerChannelAdmin = new EventServerChannelAdminImpl(
		eventServerName);

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
	    String cosTimeName = "MEZZO-COSTIME";
	    System.out.println("Mezzo COS Event Server \"" + eventServerName
		    + "\" is subscribing to COS Time " + cosTimeName);
	    TimeClient.init(null).subscribeToTimeService(cosTimeName,
		    new CallbackTimeImpl());

	    reloadPersistedChannel(eventServerName);
	    
	    log.info("Mezzo COS Event Server \"" + eventServerName
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

    /**
     * UC
     * @param eventServerName
     */
    private void reloadPersistedChannel(String eventServerName) {
	log.info("Mezzo COS Event Server \"" + eventServerName
	    + "\" is loading persisted data...");
	
	// chargement des éléments de la base
	JdbcDAO dao = DAOFactory.getJdbcDAO();
	Collection<Channel> col = dao.findAllChannel();
	ConsumerModel consumer;
	
	//ChannelDAO dao = DAOFactory.getChannelDAO();
	//Collection<Channel> col = dao.findAll();
	if (col != null) {
	for (Channel c : col) {
	    	SortedSet<EventModel> setevents = dao.findEventByChannel(c.getId());
		c.setEvents(setevents);
		Map<String,ConsumerModel> cmap =  dao.findConsumerByChannel(c.getId());	
		for (Iterator<ConsumerModel> i = cmap.values().iterator() ; i.hasNext() ;){
			consumer = i.next();
			SortedSet<EventModel> events =  dao.findEventByConsumer(consumer.getId());
			consumer.setEvents(events);
		}
		c.setConsumers(cmap);
	    EventServer.getInstance().addChannel(c);
	    // Publish the ChannelAdminImpl with Corba
	    ChannelAdminImpl cai = BFFactory.createChannelAdminImpl(c
		    .getTopic());
	    ChannelPublisher.publish(cai);
	}
	log.info("Mezzo COS Event Server \"" + eventServerName + "\" "
		+ col.size()
		+ " persisted channel loaded and published.");
	} else {
	log.info("Mezzo COS Event Server \"" + eventServerName
		+ "\" 0 persisted channel loaded and published.");
	}
    }

    public static void main(String[] args) throws InterruptedException,
	    TimeClientException, EventServerException {
	new CosEventServer(args);
    }

    /**
     * Time difference in millis between system time and time service.
     * 
     * @return difference in millis
     */
    public static long getDelta() {
	return delta;
    }

    /**
     * Set the time difference in millis between system time and time service.
     * 
     * @param delta
     *            difference in millis
     */
    public static void setDelta(long delta) {
	CosEventServer.delta = delta;
    }

}
