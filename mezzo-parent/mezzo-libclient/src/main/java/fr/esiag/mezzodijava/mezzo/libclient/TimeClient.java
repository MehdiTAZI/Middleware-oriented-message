package fr.esiag.mezzodijava.mezzo.libclient;

import java.util.Arrays;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.commons.ConfMgr;
import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizablePOATie;
import fr.esiag.mezzodijava.mezzo.costime.TimeService;
import fr.esiag.mezzodijava.mezzo.costime.TimeServiceHelper;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TimeClientException;

/**
 * Class EventClient.
 * 
 * Client API first class. Provide methods to allow Mezzo COS EVent client's
 * developper to use the service such as:
 * 
 * - resolving a channel by its topic from the CORBA CosNaming
 * 
 * - as a consumer, serving a Callback implementation to get its reference to
 * send to the Event Server.
 * 
 * UC n°: US14,US15 (+US children)
 * 
 * Program argmuments to use when launching a client:
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
public final class TimeClient {

    private static Logger log = LoggerFactory.getLogger(TimeClient.class);

    private static TimeClient instance;

    /**
     * Give a singleton instance of EventClient with his orb and his nameservice
     * initialized.
     * 
     * @param args
     *            Command line arguments
     * @param props
     *            Properties
     * @return a initialized singleton instance of EventClient
     * @throws TimeClientException
     *             Cannot init a resource (ORB or NameService) with this
     *             configuration
     */
    public static synchronized TimeClient init(String[] args, Properties props)
	    throws TimeClientException {
	if (instance == null) {
	    String[] cmdArgs = args == null ? null : Arrays.copyOf(args,
		    args.length);
	    instance = new TimeClient(cmdArgs, props);
	}
	return instance;
    }

    /**
     * Give a singleton instance of EventClient with his orb and his nameservice
     * initialized.
     * 
     * @return a initialized singleton instance of EventClient
     * @throws TimeClientException
     *             Cannot init a resource (ORB or NameService) with this
     *             configuration
     */
    public static synchronized TimeClient init() throws TimeClientException {
	if (instance == null) {
	    instance = new TimeClient(null, null);
	}
	return instance;
    }

    private POA callbacksPOA;

    private NamingContextExt nce;

    private ORB orb;

    /**
     * ORB Properties
     */
    private Properties props;

    /**
     * Event Client private Constructor. Use EventClient.init
     * 
     * @param args
     *            Program Arguments
     * @param properties
     *            optional properties
     * @throws TimeClientException
     *             Initialization failure
     */
    private TimeClient(String[] args, Properties properties)
	    throws TimeClientException {
	log.info("Initilazing Mezzo Time Client...");
	props = properties;
	if (props == null) {
	    props = ConfMgr.loadProperties("timeclient_default","timeclient");
	}
	orb = ORB.init(args, props);
	Object nceObj = null;
	try {
	    nceObj = orb.resolve_initial_references("NameService");
	} catch (InvalidName e) {
	    // TODO log here
	    log.debug("Cannot resolve NameService");
	    throw new TimeClientException("Cannot resolve NameService", e);
	}
	nce = NamingContextExtHelper.narrow(nceObj);
	log.info("Mezzo Time Client initialized.");
    }

    public ORB getOrb() {
	return orb;
    }

    /**
     * Return a Time Service instance by its name from the name service.
     * 
     * @param timeServerName
     *            Name of the Time Service in the Name Service
     * @return time service instance
     * @throws TimeClientException
     *             In case of failure of resolving Time Service.
     */
    public TimeService resolveTimeService(String timeServerName)
	    throws TimeClientException {
	Object channelObj = null;
	try {
	    channelObj = nce.resolve_str("timeServer/" + timeServerName);
	} catch (NotFound e) {

	    throw new TimeClientException("Cannot find the Time Service '"
		    + timeServerName + "'", e);
	} catch (CannotProceed e) {

	    throw new TimeClientException("Cannot resolve the Time Service", e);
	} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {

	    throw new TimeClientException("Invalid topic name", e);
	}
	return TimeServiceHelper.narrow(channelObj);
    }

    /**
     * Convenient method to resove a Time service by its name (fist aguments),
     * serve a time callback (second argument) et subscribe it in the time
     * service.
     * 
     * @param timeServerName
     *            Name of the Time Service in the Name Service
     * @param callbackConsumerImplementation
     *            implementation of callbackConsumerOperation
     * @param timeSpan
     *            refresh delay
     * @throws TimeClientException
     */
    public void subscribeToTimeService(String timeServiceName,
	    SynchronizableOperations callbackTimeImplementation, long timeSpan)
	    throws TimeClientException {
	TimeService service = resolveTimeService(timeServiceName);
	Synchronizable callbackIOR = serveCallbackTime(callbackTimeImplementation);
	try {
	    service.subscribe(callbackIOR, timeSpan);
	} catch (AlreadyRegisteredException e) {
	    throw new TimeClientException("Deja abonne au COS Time "
		    + timeServiceName, e);
	}
    }

    /**
     * Serve a CallbackTime and return its IOR.
     * 
     * @param callbackConsumerImplementation
     *            implementation of callbackConsumerOperation
     * @return IOR CallbackConsumer
     * @throws TimeClientException
     */
    public Synchronizable serveCallbackTime(
	    SynchronizableOperations callbackTimeImplementation)
	    throws TimeClientException {
	Object rootPOAObj = null;
	try {
	    rootPOAObj = orb.resolve_initial_references("RootPOA");
	} catch (InvalidName e) {
	    // TODO log here
	    throw new TimeClientException("Cannot resolve RootPOA", e);
	}
	// TODO make a child POA to handle callbacks
	callbacksPOA = POAHelper.narrow(rootPOAObj);
	try {
	    callbacksPOA.the_POAManager().activate();
	} catch (AdapterInactive e) {
	    throw new TimeClientException("Cannot activate the RootPOAManager",
		    e);
	}
	// create a tie, with servant being the delegate.
	SynchronizablePOATie tie = new SynchronizablePOATie(
		callbackTimeImplementation, callbacksPOA);

	// obtain the objectRef for the tie
	Synchronizable href = tie._this(orb);
	// return the object and
	return href;
    }

}
