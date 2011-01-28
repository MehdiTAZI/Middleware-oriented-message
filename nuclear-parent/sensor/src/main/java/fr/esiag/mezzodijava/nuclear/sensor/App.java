package fr.esiag.mezzodijava.nuclear.sensor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;


public class App 
{
	public static void main( String[] args ) throws UnknownHostException, IOException
	{
		GenerateMessage gm= new GenerateMessage();
		Socket socket = new Socket("127.0.0.1",8080);
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true);
		Capteur capteur=new Capteur(new TextFile());
		Vector<String> data=capteur.getData();
		for(String message:data){
			writer.println(gm.randomMessage());
			
			//writer.println(message);//envoie de Message au Sensorsdatasupplier     
		}
		System.out.println("END");     // message de terminaison 
		writer.println("END") ;
		writer.close();
		socket.close();
	}
}