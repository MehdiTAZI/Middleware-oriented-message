package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

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

import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdminHelper;

public class CAAgentProcess {

    public CAAgentProcess(String args[]) {
	try {
	    Properties props = new Properties();
	    try {
		props.load(this.getClass().getClassLoader()
			.getResourceAsStream("eventclient.properties"));
	    } catch (IOException e) {
		e.printStackTrace();

	    }
	    ORB orb = ORB.init(args, props);
	    POA rootPOA = POAHelper.narrow(orb
		    .resolve_initial_references("RootPOA"));
	    rootPOA.the_POAManager().activate();
	    Object objRef = orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

	    System.out.println("***** UC07 - US76 - Nominal - Creation d'Event Channel *****");
	    String nuclearChannelName = "nuclear sensor";
	    System.out.println("Asking creation of Channel \""
		    + nuclearChannelName + "\" on server \"" + args[0]
		    + "\"...");
	    EventServerChannelAdmin channelAdmin = EventServerChannelAdminHelper
		    .narrow(ncRef.resolve_str(args[0]));
	    long id = -1;
	    try {
		id = channelAdmin.createChannel(nuclearChannelName, 3);
		System.out.println("Done.");
	    } catch (ChannelAlreadyExistsException e) {
		System.out.println("Error : " + e.toString());
	    }

	    String injectorSystemChannelName = "injector system state";
	    System.out.println("Asking creation of Channel \""
		    + injectorSystemChannelName + "\" on server \"" + args[1]
		    + "\"...");
	    EventServerChannelAdmin channelAdmin2 = EventServerChannelAdminHelper
		    .narrow(ncRef.resolve_str(args[1]));
	    long id2 = -1;
	    try {
		id2 = channelAdmin2.createChannel(injectorSystemChannelName, 3);
		System.out.println("Done.");
	    } catch (ChannelAlreadyExistsException e) {
		System.out.println("Error : " + e.toString());
	    }
	    
	    /*pause("Appuyer sur une touche pour creer une deuxieme fois le canal nuclear sensors");
	    System.out.println("\n***** UC07 - US159 - Exceptionnel 1 - Creation d'Event Channel deja existant *****");
	    System.out.println("Asking creation of Channel \""
		    + nuclearChannelName + "\" on server \"" + args[0]
		    + "\"...");
	    try {
		id = channelAdmin.createChannel(nuclearChannelName, 3);
		System.out.println("Done.");
	    } catch (ChannelAlreadyExistsException e) {
		System.out.println("Error : " + e.toString());
	    }*/
	    
	    /*pause("Appuyer sur une touche pour modifier le canal nuclear sensors");
	    
	    System.out.println("\n***** UC07 - US166 - alternatif 2 - modifier la capacite d'un canal *****");
	    
	    try {
		System.out.println("Asking for modification of Channel \""
			    + nuclearChannelName + "\" on server \"" + args[0]
			    + "\"...");
		channelAdmin.changeChannelCapacity(id, 4);
		System.out.println("Done.");
	    } catch (ChannelNotFoundException e) {
		System.out.println(e.toString());
	    } catch (CannotReduceCapacityException e) {
		System.out.println("Error : " + e.toString());
	    }*/
	    
	    pause("Appuyer sur une touche pour reduire la capacite du canal injector");
	    System.out.println("\n***** UC07 - US167 - exceptionnnel 3 - reduire la capacite d'un canal *****");
	    try {
		System.out.println("Asking for modification of Channel \""
			    + injectorSystemChannelName + "\" on server \"" + args[1]
			    + "\"...");
		channelAdmin2.changeChannelCapacity(id2, 2);
		System.out.println("Done.");
	    } catch (ChannelNotFoundException e) {
		System.out.println("Error : " + e.toString());
	    } catch (CannotReduceCapacityException e) {
		System.out.println("Error : " + e.toString());
	    }
	    
	    /*pause("Appuyer sur une touche pour supprimer le canal soudan");
	    System.out.println("\n***** UC07 - US160 - alternatif 1 - supprimer un canal *****");
	    
	    try {
		System.out.println("Asking for destruction of Channel \""
			    + nuclearChannelName + "\" on server \"" + args[1]
			    + "\"...");
		channelAdmin.destroyChannel(id);
		System.out.println("Done.");
	    } catch (ChannelNotFoundException e) {
		System.out.println("Error : " + e.toString());
	    }
	    
	    pause("Appuyer sur une touche pour modifier la capacite d'un canal inexistant");
	    System.out.println("\n***** UC07 - US161 - exceptionnel 2 - modifier ou supprimer un canal inexistant *****");
	    try {
		System.out.println("Asking for destruction of Channel \""
			    + nuclearChannelName + "\" on server \"" + args[1]
			    + "\"...");
		channelAdmin2.destroyChannel(id);
		System.out.println("Done.");
	    } catch (ChannelNotFoundException e) {
		System.out.println("Error : " + e.toString());
	    }*/

	    System.out.println("ALL DONE");
	} catch (InvalidName e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (AdapterInactive e) {
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
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	new CAAgentProcess(args);
    }

    public static void pause(String msg) {
	try {
	    Thread.sleep(200);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.print(msg + "... ");

	Scanner keyIn = new Scanner(System.in);
	keyIn.nextLine();

    }

}
