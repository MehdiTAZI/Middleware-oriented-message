package fr.esiag.mezzodijava.mezzo.libclient;

import java.io.IOException;
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

import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdminHelper;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

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
public final class EventClient {

	/**
	 * Default Event Client Property File.
	 */
	private final static String CLIENT_PROPERTIES = "eventclient.properties";

	private static EventClient instance;

	/**
	 * Give a singleton instance of EventClient with his orb and his nameservice
	 * initialized.
	 * 
	 * @param args
	 *            Command line parameters
	 * @return a initialized singleton instance of EventClient
	 * @throws EventClientException
	 *             Cannot init a resource (ORB or NameService) with this
	 *             configuration
	 */
	public static synchronized EventClient init(String[] args)
			throws EventClientException {
		if (instance == null) {
			// // Properties à externaliser
			// props = new Properties();
			// props.setProperty("ORBInitRef.NameService",
			// "corbaloc::127.0.0.1:1050/NameService");
			// props.setProperty("jacorb.home", System.getenv("JACORB_HOME"));
			// props.setProperty("org.omg.CORBA.ORBClass",
			// "org.jacorb.orb.ORB");
			// props.setProperty("org.omg.CORBA.ORBSingletonClass",
			// "org.jacorb.orb.ORBSingleton");
			// // props.setProperty("java.endorsed.dirs",
			// // System.getenv("JACORB_HOME") + "/lib");
			String[] cmdArgs = args == null ? null : Arrays.copyOf(args,
					args.length);
			instance = new EventClient(cmdArgs, null);
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

	//
	// private String[] args = { "-ORBInitRef",
	// "NameService=corbaloc::127.0.0.1:1050/NameService",
	// "-Djacorb.home=C:\\mezzodev\\jacorb-2.3.1",
	// "-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB",
	// "-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton" };

	/**
	 * Event Client private Constructor. Use EventClient.init
	 * 
	 * @param args
	 *            Program Arguments
	 * @param properties
	 *            optional properties
	 * @throws EventClientException
	 *             Initialization failure
	 */
	private EventClient(String[] args, Properties properties)
			throws EventClientException {
		props = properties;
		if (props == null) {
			props = new Properties();
			try {
				props.load(this.getClass().getClassLoader()
						.getResourceAsStream(EventClient.CLIENT_PROPERTIES));
			} catch (IOException e) {
				// TODO log here
				throw new EventClientException(
						"Error in opening client property file", e);
			}
		}
		orb = ORB.init(args, props);
		Object nceObj = null;
		try {
			nceObj = orb.resolve_initial_references("NameService");
		} catch (InvalidName e) {
			// TODO log here
			throw new EventClientException("Cannot resolve NameService", e);
		}
		nce = NamingContextExtHelper.narrow(nceObj);
	}

	public ORB getOrb() {
		return orb;
	}

	/**
	 * Resolve a Channel with its topic using the NameService declared in
	 * configuration.
	 * 
	 * @param topic
	 *            Channel to find topic
	 * @return an instance of Channel (distant object)
	 * @throws EventClientException
	 *             when name resolution is wrong
	 * @throws TopicNotFoundException
	 */
	public ChannelAdmin resolveChannelByTopic(String topic)
			throws EventClientException, TopicNotFoundException {
		Object channelObj = null;
		try {
			channelObj = nce.resolve_str(topic);
			// TODO Design a fail-over to switch between several NameService
			// before throwing exception
		} catch (NotFound e) {
			// TODO log here
			throw new TopicNotFoundException("Cannot find the Topic '" + topic
					+ "'", e);
		} catch (CannotProceed e) {
			// TODO log here
			throw new EventClientException("Cannot resolve the channel", e);
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO log here
			throw new EventClientException("Invalid topic name", e);
		}
		return ChannelAdminHelper.narrow(channelObj);
	}

	/**
	 * Server a CallBackConsumer and return its IOR.
	 * 
	 * @param callbackConsumerImplementation
	 *            implementation of callbackConsumerOperation
	 * @return IOR CallbackConsumer
	 * @throws EventClientException
	 */
	public CallbackConsumer serveCallbackConsumer(
			CallbackConsumerOperations callbackConsumerImplementation)
			throws EventClientException {
		Object rootPOAObj = null;
		try {
			rootPOAObj = orb.resolve_initial_references("RootPOA");
		} catch (InvalidName e) {
			// TODO log here
			throw new EventClientException("Cannot resolve RootPOA", e);
		}
		// TODO make a child POA to handle callbacks
		callbacksPOA = POAHelper.narrow(rootPOAObj);
		try {
			callbacksPOA.the_POAManager().activate();
		} catch (AdapterInactive e) {
			throw new EventClientException(
					"Cannot activate the RootPOAManager", e);
		}
		// create a tie, with servant being the delegate.
		CallbackConsumerPOATie tie = new CallbackConsumerPOATie(
				callbackConsumerImplementation, callbacksPOA);

		// obtain the objectRef for the tie
		CallbackConsumer href = tie._this(orb);
		// return the object and
		return href;
	}

}
