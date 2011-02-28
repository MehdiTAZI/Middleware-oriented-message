package fr.esiag.mezzodijava.nuclear.sensor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/*
 *  Mettre en program arguments :
 *  pression.txt radioactivite.txt temperature.txt
 */
public class AppCapteur 
{
	public static void main( String[] args ) throws UnknownHostException, IOException
	{
		
		Socket socket = new Socket("127.0.0.1",9191);
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true);
		Capteur capteur=new Capteur(new TextFile());
		Vector<String> data = new Vector<String>();
		for (String arg : args){
			System.out.println("CHARGEMENT DE : "+ arg);
			data.addAll(capteur.getData(arg));
		}
		for(String message:data){
			System.out.println(message);
			writer.println(message);//envoie de Message au Sensorsdatasupplier     
			try {
			    Thread.sleep(2000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}
		System.out.println("END");     // message de terminaison 
		writer.println("END") ;
		writer.close();
		socket.close();
	}
	
}