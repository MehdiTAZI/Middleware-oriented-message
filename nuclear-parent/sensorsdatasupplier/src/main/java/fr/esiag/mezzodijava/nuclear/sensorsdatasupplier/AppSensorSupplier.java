package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.OutputStream;

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
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;
import fr.esiag.nuclear.commons.model.Pression;
import fr.esiag.nuclear.commons.model.RadioActivite;
import fr.esiag.nuclear.commons.model.Temperature;

public class AppSensorSupplier 
{
	private static ORB orb;

	private static Event createMessage (String s){


		//System.out.println(s);
		StringBuilder sb=new StringBuilder();
		long code = 0;
		long value=0;
		String type="";

		StringTokenizer st= new StringTokenizer(s,"/");
		code = Long.parseLong(st.nextToken());
		type=st.nextToken();
		
		value=Long.valueOf(st.nextToken());
		
		Event e=null;
		
		Random random=new Random();
		int randomNumber=random.nextInt()*50;
		if(randomNumber<5) 
			return createStringEvent(s);
		if(type.toLowerCase().trim().equals("temperature")){
			Temperature t=new Temperature(value, "°C");
			e=EventFactory.createEventObject(1, 5000,t,"Temperature");
		}
		else if(type.toLowerCase().trim().equals("pression")){
			Pression p=new Pression(value,"Pascal");
			e=EventFactory.createEventObject(1, 5000,p,"Pression");
		}
		else if(type.toLowerCase().trim().equals("radioactivite")){
			RadioActivite ra=new RadioActivite(value, "HDA");
			e=EventFactory.createEventObject(1, 5000,ra,"RadioActivite");
		}
		
		
		return e;
	}

	public static Event createStringEvent(String s){
		StringBuilder sb=new StringBuilder();
		long code = 0;
		String value="";
		String type="";

		StringTokenizer st= new StringTokenizer(s,"/");
		

		if(st.hasMoreElements())
			code = Long.parseLong(st.nextToken());
		else
			System.err.println("miss-formed Event Structure : can't find code element");
		if(st.hasMoreElements())
			sb.append(st.nextToken()+"|");
		else
			System.err.println("miss-formed Event Structure : can't find type element");
		if(st.hasMoreElements()){
			value=st.nextToken();
			sb.append(value);
		}	
		else
			System.err.println("miss-formed Event Structure : can't find data element");

		Event e=EventFactory.createEventString(1, 5000,sb.toString());
		
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
		ec.getOrb().run();
	}
}
