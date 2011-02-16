package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;



public class AppSensorSupplier 
{
	private static ORB orb;
	public static void main( String[] args ) throws NumberFormatException, IOException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, EventClientException, TopicNotFoundException, NotConnectedException
	{
	    	System.out.println("Nuclear Sensors Data Supplier creation...");
		EventClient ec = EventClient.init(null);
		orb = ec.getOrb();
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("nuclear sensor");
		System.out.println("Nuclear Sensors Data Supplier creation...");
		ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier();
		System.out.println("Done.");
		System.out.println("Nuclear Sensors Data Supplier connection...");
		supplierProxy.connect();
		System.out.println("Done.");
		ServerSocket socketServer = new ServerSocket(9191);
		System.out.println("Waiting for sensor...");
		Socket socketClient = socketServer.accept();
		System.out.println("Connected sensor !!!!!");
		//accept permet d'accepter les connexions client
		// Un BufferedReader permet de lire les message envoyer par le client.
		BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		Random r=new Random();
		while (true) {
			String str = reader.readLine();          // lecture du message ligne par ligne
			if (str.equals("END")) break;
			System.out.println(str);
			Event e = new Event((new Date()).getTime(),str,0,6565);
			EventInfo eventInfo = new EventInfo(e);
			
			//si c'est une alerte on le marque puis on l'envoie
			if (eventInfo.isAlerte()){ 
				eventInfo.setCode(923);				
			}else{
				eventInfo.setCode(42);
			}
			String data = eventInfo.getCode()+"/"+eventInfo.getType()+"/"+eventInfo.getData();
			
			Event event = new Event((new Date()).getTime(),data,r.nextInt(10),64663);
			
			supplierProxy.push(event);   
		}
		
		supplierProxy.afficher();
		reader.close();
		socketClient.close();
		ec.getOrb().run();
	}
}
