package fr.esiag.mezzodijava.mezzo.cosadmin;

import java.util.List;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.commons.ConfMgr;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

public class CosManager {

    private static Logger log = LoggerFactory.getLogger(CosManager.class);

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
     * @throws EventClientException
     *             Initialization failure
     */
    public CosManager(String[] args, Properties properties)
	    throws EventClientException {
	log.info("Initilazing Manager...");
	props = properties;
	if (props == null) {
	    props = ConfMgr.loadProperties("manager_default", "manager");
	    System.out.println("eventClient Properties loaded");
	}
	orb = ORB.init(args, props);
	System.out.println("ORB initialised");
	Object nceObj = null;
	try {
	    System.out.println("affiche toi !");
	    System.out.println("resolving Nameservice");
	    nceObj = orb.resolve_initial_references("NameService");
	} catch (InvalidName e) {
	    // TODO log here
	    System.out.println("Catch Exception Resolving!");
	    throw new EventClientException("Cannot resolve NameService", e);
	}
	System.out.println("Start NameService narrowing");
	nce = NamingContextExtHelper.narrow(nceObj);
	log.info("Mezzo Client initialized.");
    }

    public List<String> listEventServers() {
	return null;

    }

    public List<String> listTimeServers() {
	return null;

    }

    public List<String> listEventChannel() {
	return null;
    }

    public void addChannel(String serverName, String channelName) {

    }

    public void modChannel(String channelName, int capacity) {

    }

    public void delChannel(String channelName) {

    }
}
