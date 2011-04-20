package fr.esiag.mezzodijava.nuclear.sensor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Mettre en program arguments :
 *  <adresse IP> pression.txt radioactivite.txt temperature.txt
 *  
 *  ex :
 *  
 *  127.0.0.1 pression.txt
 */
public class AppCapteur {
    public static void main(String[] args) throws UnknownHostException,
	    IOException {

	Socket socket = new Socket(args[0], 9191);
	for (int i = 1; i < args.length; i++) {

	}
	PrintWriter writer = new PrintWriter(new BufferedWriter(
		new OutputStreamWriter(socket.getOutputStream())), true);
	Capteur capteur = new Capteur(new TextFile());
	List<String> data = new ArrayList<String>();
	for (int i = 1; i < args.length; i++) {
	    System.out.println("CHARGEMENT DE : " + args[i]);
	    data.addAll(capteur.getData(args[i]));
	}
	for (String message : data) {
	    System.out.println(message);
	    writer.println(message);// envoie de Message au Sensorsdatasupplier
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	System.out.println("END"); // message de terminaison
	writer.println("END");
	writer.close();
	socket.close();
    }

}