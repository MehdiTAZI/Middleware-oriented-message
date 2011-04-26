package fr.esiag.mezzodijava.mezzo.cosadmin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.commons.ConfMgr;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

public class CosManager {

    private static Logger log = LoggerFactory.getLogger(CosManager.class);

    private NamingContextExt nce;

    private ORB orb;

    private String[] args;

    /**
     * ORB Properties
     */
    private Properties channelProps = new Properties();
    
    private Properties props = new Properties();

    private void loadProps() throws Exception {
	// create and load default properties
	FileInputStream in = new FileInputStream(
		System.getProperty("user.home")
			+ System.getProperty("file.separator")
			+ "cosadmin.properties");
	channelProps.load(in);
	in.close();
    }

    private void saveProps() throws Exception {
	// create and load default properties
	FileOutputStream out = new FileOutputStream(
		System.getProperty("user.home")
			+ System.getProperty("file.separator")
			+ "cosadmin.properties");
	channelProps.store(out, "---No Comment---");
	out.close();
    }

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
	    System.out.println("resolving Nameservice");
	    nceObj = orb.resolve_initial_references("NameService");
	} catch (InvalidName e) {
	    // TODO log here
	    System.out.println("Catch Exception Resolving!");
	    throw new EventClientException("Cannot resolve NameService", e);
	}
	System.out.println("Start NameService narrowing");
	nce = NamingContextExtHelper.narrow(nceObj);
	try {
		loadProps();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	log.info("Manager initialized.");
    }

    public List<String> listEventServers() {
	return listBindings("eventServer");
    }

    private List<String> listBindings(String contextName) {
	List<String> res = new ArrayList<String>();
	try {
	    NamingContext context = NamingContextHelper.narrow(nce
	    	.resolve_str(contextName));
	    BindingListHolder listHolder = new BindingListHolder();
	    BindingIteratorHolder iteratorHolder = new BindingIteratorHolder();
	    context.list(10, listHolder, iteratorHolder);
	    for (Binding binding : listHolder.value) {
	        res.add(binding.binding_name[0].id);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return res;
    }

    public List<String> listTimeServers() {
	return listBindings("timeServer");
    }

    public List<String> listEventChannel() {
	return listBindings("eventChannel");
    }

    public void addChannel(String serverName, String channelName, int capacity)
	    throws Exception {
	EventClient ec = EventClient.init(args);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(serverName);
	long id = esca.createChannel(channelName, capacity);
	channelProps.put(channelName + ".server", serverName);
	channelProps.put(channelName + ".id", ""+id);
	channelProps.put(channelName + ".capacity", ""+capacity);
	saveProps();
    }

    public void modChannel(String channelName, int capacity) throws Exception {
	EventClient ec = EventClient.init(args);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(channelProps
			.getProperty(channelName + ".server"));
	esca.changeChannelCapacity(
		new Long(channelProps.getProperty(channelName + ".id")),
		capacity);
	channelProps.put(channelName + ".capacity", ""+capacity);
	saveProps();
    }

    public void delChannel(String channelName) throws Exception {
	EventClient ec = EventClient.init(args);
	EventServerChannelAdmin esca = ec
		.resolveEventServerChannelAdminByEventServerName(channelProps
			.getProperty(channelName + ".server"));
	esca.destroyChannel(new Long(channelProps.getProperty(channelName
		+ ".id")));
	channelProps.remove(channelName + ".server");
	channelProps.remove(channelName + ".id");
	channelProps.remove(channelName + ".capacity");
	saveProps();
    }
}
