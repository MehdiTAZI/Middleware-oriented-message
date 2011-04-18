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

import fr.esiag.mezzodijava.mezzo.costime.TimeService;
import fr.esiag.mezzodijava.mezzo.costime.TimeServiceHelper;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TimeClientException;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollector;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollectorHelper;

/**
 * 
 * @author MEZZODIJAVA
 * manage client
 *
 */
public class ManagerClient {
	private final static String CLIENT_PROPERTIES = "eventclient.properties";
	private static ManagerClient instance=null;
	private POA callbacksPOA;
	private NamingContextExt nce;
	private ORB orb;
	private Properties props;

	/**
	 * init the managerClient
	 * @param args properties for the managerClient
	 * @return ManagerClient
	 */
	public static synchronized ManagerClient init(String[] args) {
		if (instance == null) {

			String[] cmdArgs = args == null ? null : Arrays.copyOf(args,
					args.length);
			instance = new ManagerClient(cmdArgs, null);
		}
		return instance;
	}

	
	private ManagerClient(String[] args, Properties properties) {
		System.out.println("Initilazing Mezzo Time Client...");
		props = properties;
		if (props == null) {
			props = new Properties();
			try {
				props.load(this.getClass().getClassLoader()
						.getResourceAsStream(ManagerClient.CLIENT_PROPERTIES));
			} catch (Exception e) {
			}
			orb = ORB.init(args, props);
			Object nceObj = null;
			try {
				nceObj = orb.resolve_initial_references("NameService");
			} catch (Exception e) {
			}
			nce = NamingContextExtHelper.narrow(nceObj);
		}
	}

	/**
	 * 
	 * @return the ORB
	 */
	public ORB getOrb() {
		return orb;
	}

	/**
	 * 
	 * @param managerName name of the managerClient
	 * @return a CosInfoCollector
	 */
	public CosInfoCollector resolveManagerService(String managerName){
		Object managerObj = null;
		try {
			managerObj = nce.resolve_str(managerName);
		} catch (Exception e) {
		}
		return CosInfoCollectorHelper.narrow(managerObj);
	}

}
