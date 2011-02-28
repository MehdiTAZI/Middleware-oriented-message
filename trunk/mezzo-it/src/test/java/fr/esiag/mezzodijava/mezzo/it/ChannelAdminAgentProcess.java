package fr.esiag.mezzodijava.mezzo.it;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.EventServerChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

public class ChannelAdminAgentProcess {
	
	private String channelName="MEZZO";
	private String eventServerName="MEZZO-SERVER";

	public ChannelAdminAgentProcess(String[] args) throws EventClientException, ChannelAlreadyExistsException, ChannelNotFoundException, AlreadyRegisteredException, InterruptedException, NotRegisteredException, CannotReduceCapacityException {


		EventClient ec = EventClient.init(null);


		//creation du channel 
		EventServerChannelAdmin eventServerChannelAdmin=ec.resolveEventServerChannelAdminByEventServerName(eventServerName);
		long id=eventServerChannelAdmin.createChannel(channelName, 20);
		System.out.println("Creation du channel "+channelName);
		System.out.println("UID "+ id);

		//l'abonnement et la connexion du consumer au channel crée

		
		Thread.sleep(20000);
		eventServerChannelAdmin.changeChannelCapacity(id, 30);
		System.out.println("la modification du capacity");
		
		
		System.out.println("la Suppression du Channel dans 20s...");
		Thread.sleep(300000);
				
						
		
		eventServerChannelAdmin.destroyChannel(id);
		System.out.println("Suppression du channel "+channelName);

		System.out.println("ALL DONE");
		ORB orb = BFFactory.createOrb(null, null);
		orb.run();
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws EventClientException 
	 * @throws NotRegisteredException 
	 * @throws AlreadyRegisteredException 
	 * @throws ChannelNotFoundException 
	 * @throws ChannelAlreadyExistsException 
	 * @throws CannotReduceCapacityException 
	 */
	public static void main(String[] args) throws ChannelAlreadyExistsException, ChannelNotFoundException, AlreadyRegisteredException, NotRegisteredException, EventClientException, InterruptedException, CannotReduceCapacityException {

		new ChannelAdminAgentProcess(args);
	}

}
