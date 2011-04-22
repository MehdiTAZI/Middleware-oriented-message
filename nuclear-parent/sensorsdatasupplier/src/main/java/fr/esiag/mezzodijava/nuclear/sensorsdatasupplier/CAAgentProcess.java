package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.util.Scanner;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

public class CAAgentProcess {

    public CAAgentProcess(String args[]) {
	try {
	    EventClient ec = EventClient.init(args);
	    System.out.println("***** Creation d'Event Channel *****");
	    String nuclearChannelName = "nuclear sensor";
	    System.out.println("Asking creation of Channel \""
		    + nuclearChannelName + "\" on server \"" + args[0]
		    + "\"...");
	    EventServerChannelAdmin channelAdmin = ec.resolveEventServerChannelAdminByEventServerName(args[0]);
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
	    EventServerChannelAdmin channelAdmin2 = ec.resolveEventServerChannelAdminByEventServerName(args[1]);
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
	    
	    //pause("Mise en pause");
	    /*System.out.println("\n***** UC07 - US167 - exceptionnnel 3 - reduire la capacite d'un canal *****");
	    try {
		System.out.println("Asking for modification of Channel \""
			    + injectorSystemChannelName + "\" on server \"" + args[1]
			    + "\"...");
		channelAdmin2.changeChannelCapacity(id2, 2);
		
		System.out.println("Done.");
		
		pause("Appuyer sur une touche pour detruire un canal nuclear sensor");
	    System.out.println("\n***** UC07 - US161 - exceptionnnel 2 - detruire un canal *****");
		channelAdmin.destroyChannel(id);
		
	    } catch (ChannelNotFoundException e) {
		System.out.println("Error : " + e.toString());
	    } catch (CannotReduceCapacityException e) {
		System.out.println("Error : " + e.toString());
	    }*/
	    
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
	} catch (EventClientException e) {
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
