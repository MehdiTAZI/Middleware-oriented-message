package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;


public class App 
{
	private static ORB orb;
	public static void main( String[] args ) throws NumberFormatException, IOException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, EventClientException, TopicNotFoundException, NotConnectedException
	{
		EventClient ec = EventClient.init(null);
		orb = ec.getOrb();
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("MEZZO");

		ProxyForPushSupplier supplierProxy = channelAdmin
				.getProxyForPushSupplier();
		supplierProxy.connect();
		
		ServerSocket socketServer = new ServerSocket(8080);
		Socket socketClient = socketServer.accept();
		System.out.println("Sensor Connecté !!!!!");
		//accept permet d'accepter les connexions client
		// Un BufferedReader permet de lire les message envoyer par le client.
		BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		while (true) {
			String str = reader.readLine();          // lecture du message ligne par ligne
			if (str.equals("END")) break;
			supplierProxy.push(new Event((new Date()).getTime(),str));   
		}
		reader.close();
		socketClient.close();
	}
}
