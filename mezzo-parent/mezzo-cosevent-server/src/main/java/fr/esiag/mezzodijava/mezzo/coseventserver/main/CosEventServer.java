package fr.esiag.mezzodijava.mezzo.coseventserver.main;

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

import fr.esiag.mezzodijava.mezzo.commons.ConfMgr;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDAO;
import fr.esiag.mezzodijava.mezzo.coseventserver.exceptions.EventServerException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.CallbackTimeImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.CosInfoCollectorImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.EventServerChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.publisher.ChannelPublisher;
import fr.esiag.mezzodijava.mezzo.libclient.TimeClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TimeClientException;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollectorPOATie;
import fr.esiag.mezzodijava.mezzo.servercommons.CORBAUtils;

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
     * Configuration properties
     */
    public static Properties properties;

    private Logger log = LoggerFactory.getLogger(CosEventServer.class);

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
    public CosEventServer(String[] args) throws InterruptedException,
	    TimeClientException {
	String eventServerName;
	if (args.length == 1) {
	    eventServerName = args[0];
	} else {
	    eventServerName = properties.getProperty("eventserver.name");
	}
	orb = BFFactory.createOrb(args, properties);
	String cosTimeName = properties.getProperty(
		"eventserver.timeclient.servername", "MEZZO-COSTIME");
	long cosTimeRefreshDelay = ConfMgr.getLongValue(properties,
		"eventserver.timeclient.refreshdelay", 1000);

	EventServer.getInstance().setServerName(eventServerName);
	EventServerChannelAdminImpl eventServerChannelAdmin = new EventServerChannelAdminImpl(
		eventServerName);

	try {
	    POA poa = POAHelper.narrow(orb
		    .resolve_initial_references("RootPOA"));
	    poa.the_POAManager().activate();
	    NamingContextExt nc = NamingContextExtHelper.narrow(orb
		    .resolve_initial_references("NameService"));

	    // nc.rebind(nc.to_name(channelName),
	    // poa.servant_to_reference(new
	    // ChannelAdminPOATie(channelAdminImpl)));
	    CORBAUtils.createContext(nc, "eventServer");
	    nc.rebind(nc.to_name("eventServer/" + eventServerName), poa
		    .servant_to_reference(new EventServerChannelAdminPOATie(
			    eventServerChannelAdmin)));

	    CORBAUtils.createContext(nc, "eventMonitor");
	    nc.rebind(nc.to_name("eventMonitor/" + eventServerName), poa
		    .servant_to_reference(new CosInfoCollectorPOATie(
			    new CosInfoCollectorImpl())));

	    // Subscribe to COSTime
	    log.info("Mezzo COS Event Server \"" + eventServerName
		    + "\" is subscribing to COS Time " + cosTimeName);
	    TimeClient.init(args, properties).subscribeToTimeService(
		    cosTimeName, new CallbackTimeImpl(), cosTimeRefreshDelay);

	    // reload persisted data
	    reloadPersistedChannel(eventServerName);

	    log.info("Mezzo COS Event Server \"" + eventServerName
		    + "\" is running...");

	    orb.run();

	} catch (InvalidName e) {
	    log.error("Cos Event Error", e);
	} catch (NotFound e) {
	    log.error("Cos Event Error", e);
	} catch (CannotProceed e) {
	    log.error("Cos Event Error", e);
	} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
	    log.error("Cos Event Error", e);
	} catch (ServantNotActive e) {
	    log.error("Cos Event Error", e);
	} catch (WrongPolicy e) {
	    log.error("Cos Event Error", e);
	} catch (AdapterInactive e) {
	    log.error("Cos Event Error", e);
	}
    }

    /**
     * Reload persisted channel with theirs subscribed consumers and bind them
     * to the Name Service.
     * 
     * UC n° UC41 : CI06-Persistence
     * 
     * @param eventServerName
     *            Event Server Name.
     */
    private void reloadPersistedChannel(String eventServerName) {
	log.info("Mezzo COS Event Server \"" + eventServerName
		+ "\" is loading persisted data...");

	// chargement des éléments de la base
	JdbcDAO dao = DAOFactory.getJdbcDAO();
	Collection<Channel> col = dao.findAllChannel();
	ConsumerModel consumer;

	// ChannelDAO dao = DAOFactory.getChannelDAO();
	// Collection<Channel> col = dao.findAll();
	if (col != null) {
	    for (Channel c : col) {
		Map<String, ConsumerModel> cmap = dao.findConsumerByChannel(c
			.getId());
		for (Iterator<ConsumerModel> i = cmap.values().iterator(); i
			.hasNext();) {
		    consumer = i.next();
		    SortedSet<EventModel> events = dao
			    .findEventByConsumer(consumer.getId());
		    consumer.setEvents(events);
		}
		c.setConsumers(cmap);
		EventServer.getInstance().addChannel(c);
		// Publish the ChannelAdminImpl with Corba
		ChannelAdminImpl cai = BFFactory.createChannelAdminImpl(c
			.getTopic());
		ChannelPublisher.publish(cai);
		log.info("Mezzo COS Event Server \"" + eventServerName
			+ "\" : persisted channel \"" + c.getTopic()
			+ "\"loaded with " + cmap.size()
			+ "subscribed consummers and published.");
	    }
	    log.info("Mezzo COS Event Server \"" + eventServerName + "\" : "
		    + col.size() + " persisted channel(s) loaded and published.");
	} else {
	    log.info("Mezzo COS Event Server \"" + eventServerName
		    + "\" : 0 persisted channel loaded and published.");
	}
    }

    /**
     * Main.
     * 
     * @param args
     * @throws InterruptedException
     * @throws TimeClientException
     * @throws EventServerException
     */
    public static void main(String[] args) throws InterruptedException,
	    TimeClientException, EventServerException {
	initConf();
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

    /**
     * Load properties files.
     */
    public static void initConf() {
	properties = ConfMgr.loadProperties("eventserver_default",
		"eventserver");
    }

}
