package fr.esiag.mezzodijava.nuclear.sensorsdatasupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
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

public class AppSensorSupplier {
    private static ORB orb;

    static ProxyForPushSupplier supplierProxy;

    private static Event createMessage(String s) {

	// System.out.println(s);
	StringBuilder sb = new StringBuilder();
	long code = 0;
	long value = 0;
	String type = "";

	StringTokenizer st = new StringTokenizer(s, "/");
	code = Long.parseLong(st.nextToken());
	type = st.nextToken();

	value = Long.valueOf(st.nextToken());

	Event e = null;

//	Random random = new Random();
//	int randomNumber = random.nextInt() * 50;
//	if (randomNumber < 5)
//	    return createStringEvent(s);
	if (type.toLowerCase().trim().equals("temperature")) {
	    Temperature t = new Temperature(value, "Celsius");
	    e = EventFactory.createEventObject(1, 5000, t);
	} else if (type.toLowerCase().trim().equals("pression")) {
	    Pression p = new Pression(value, "Pascal");
	    e = EventFactory.createEventObject(1, 5000, p);
	} else if (type.toLowerCase().trim().equals("radioactivite")) {
	    RadioActivite ra = new RadioActivite(value, "Becquerel");
	    e = EventFactory.createEventObject(1, 5000, ra);
	}

	return e;
    }

    public static Event createStringEvent(String s) {
	StringBuilder sb = new StringBuilder();
	long code = 0;
	String value = "";
	String type = "";

	StringTokenizer st = new StringTokenizer(s, "/");

	if (st.hasMoreElements())
	    code = Long.parseLong(st.nextToken());
	else
	    System.err
		    .println("miss-formed Event Structure : can't find code element");
	if (st.hasMoreElements())
	    sb.append(st.nextToken() + "|");
	else
	    System.err
		    .println("miss-formed Event Structure : can't find type element");
	if (st.hasMoreElements()) {
	    value = st.nextToken();
	    sb.append(value);
	} else
	    System.err
		    .println("miss-formed Event Structure : can't find data element");

	Event e = EventFactory.createEventString(1, 5000, sb.toString());

	return e;

    }

    public static void main(String[] args) throws NumberFormatException,
	    IOException, ChannelNotFoundException,
	    MaximalConnectionReachedException,
	    EventClientException, TopicNotFoundException, NotConnectedException {
	SensorThread st[] = new SensorThread[10];
	EventClient ec = EventClient.init(null);
	orb = ec.getOrb();
	ChannelAdmin channelAdmin = ec.resolveChannelByTopic("nuclear sensor");
	System.out.println("Nuclear Sensors Data Supplier creation...");
	String idcomponent = "sensor";
	supplierProxy = channelAdmin.getProxyForPushSupplier(idcomponent);
	System.out.println("Done.");

	System.out.println("Nuclear Sensors Data Supplier connection...");
	try {
	    supplierProxy.connect();
	} catch (AlreadyConnectedException e1) {
	   ;
	}
	System.out.println("Done.");
	ServerSocket serverSocket = new ServerSocket(9191);
	Socket clientSocket;
	while (true) {
	    try {
		System.out.println("Waiting for sensor...");
		// accept permet d'accepter les connexions client
		clientSocket = serverSocket.accept();
		for (int i = 0; i <= 9; i++) {
		    if (st[i] == null) {
			(st[i] = new SensorThread(clientSocket, st)).start();
			break;
		    }
		}
	    } catch (IOException e) {
		System.out.println(e);
	    }
	}
    }

    static class SensorThread extends Thread {
	Socket clientSocket = null;
	SensorThread[] t;

	public SensorThread(Socket clientSocket, SensorThread[] t) {
	    this.clientSocket = clientSocket;
	    this.t = t;
	}

	public void run() {
	    try {
		// Un BufferedReader permet de lire les message envoyer par le
		// client.
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(clientSocket.getInputStream()));
		System.out.println("Connected sensor from "
			+ clientSocket.getInetAddress().getHostAddress() + ":"
			+ clientSocket.getPort() + " to "
			+ clientSocket.getLocalAddress().getHostAddress() + ":"
			+ clientSocket.getLocalPort());
		// lecture du message ligne par ligne
		while (true) {
		    String str = reader.readLine();
		    if (str.equals("END"))
			break;
		    // System.out.println(str);

		    Event e = createMessage(str);
		    try {
			supplierProxy.push(e);
		    } catch (ChannelNotFoundException e1) {
			System.out.println("channel not found");
		    } catch (NotConnectedException e1) {
			System.out.println("not connected");
		    }
		}

		// Clean up:
		// Set to null the current thread variable such that other
		// client could
		// be accepted by the server

		for (int i = 0; i <= 9; i++)
		    if (t[i] == this)
			t[i] = null;

		// close the reader
		// close the socket
		reader.close();
		clientSocket.close();
	    } catch (IOException e) {
	    }
	    ;
	}
    }
}
