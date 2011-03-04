package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class AppSensorSupplier 
{
	private static ORB orb;
	
	private static Event createMessage (String s){
		Header head = null;
		Body body = new Body(""); 
		long code = 0;
		
		StringTokenizer st= new StringTokenizer(s,"/");
		
		if(st.hasMoreElements())
			code = Long.parseLong(st.nextToken());
		else
			System.err.println("miss-formed Event Structure : can't find code element");
		if(st.hasMoreElements())
			body.content = st.nextToken()+"|";
		else
			System.err.println("miss-formed Event Structure : can't find type element");
		if(st.hasMoreElements())
			body.content += st.nextToken();
		else
			System.err.println("miss-formed Event Structure : can't find data element");
		
		//head=new Header(42, 1, new Date().getTime(), (int)(Math.random()*1000)+500);
		head=new Header(42, 1, new Date().getTime(), 5000);
		Event e = new Event(head,body);
		return e;
	}
	
	public static void main( String[] args ) throws NumberFormatException, IOException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, EventClientException, TopicNotFoundException, NotConnectedException
	{
	    
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
		//accept permet d'accepter les connexions client
		Socket socketClient = socketServer.accept();
		System.out.println("Connected sensor !!!!!");
		// Un BufferedReader permet de lire les message envoyer par le client.
		BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		
		while (true) {
			// lecture du message ligne par ligne
			String str = reader.readLine();   
			if (str.equals("END")) break;
			//System.out.println(str);
			
			Event e = createMessage(str);		
			supplierProxy.push(e);
		}
		
		reader.close();
		socketClient.close();
		supplierProxy.afficher();
		ec.getOrb().run();
	}
}
