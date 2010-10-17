package com.esiag.client;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.*;

public class Imprime
{
	public static void main(String args[]) throws Exception
	{
		
		System.out.println(args[0]+"  "+args[1]);
		Imprimante printerRef=null;
		if ( args.length < 2)
		{
			System.out.println("usage: java  ClassName" + " imprimante fichier");
			System.exit (-1);
		}
		
		
		String imprimante=args[0];
		String fichier=args[1];
		
		
		// A completer
		
		ORB orb=ORB.init(args, null);				
						
		
		// A completer
		String nomFichier =new String(imprimante+".OR");
		BufferedReader in=new BufferedReader(new FileReader(nomFichier));
		String ior=in.readLine();
	
		in.close();
		
		try{
		Object o=orb.string_to_object(ior);
		if(o==null)
		{
			System.out.println("erreur ior");
		}
		else{
			printerRef =ImprimanteHelper.narrow(o);
			System.out.println("OBJECT : "+ printerRef);
		}
		}catch (Exception e) {
			System.out.println("Erreur !!!!!!");
		}
		
		short maTache=0; 
		IntHolder tailleMaTache=new IntHolder();
		try
		{
			// A compléter
			StringHolder implementation=new StringHolder();
			StringHolder serveur=new StringHolder();
			StringHolder machine=new StringHolder();
			ShortHolder tacheEnCours=new ShortHolder();			
			
			// obtenir quelques informations sur l'objet distant 
			printerRef.getInfo(implementation, serveur, machine, tacheEnCours);
			
			System.out.println("\nReference obtenue pour : \n\tl'imprimante " + implementation.value + " \n\thebergee par le serveur " + serveur.value + " \n\tsur la machine " + machine.value + " \n\tsa tache courante est " + tacheEnCours.value + "\n");
			
			// demander impression
		    maTache = printerRef.printDocument (fichier, tailleMaTache);
			System.out.println("\nImpression envoyee sur " + imprimante + ", tache " +maTache + ", taille "+ tailleMaTache.value + " : "+ fichier); 
			
		}
		catch(TacheRefusee refus)
		{
			System.err.println("Pb Impression : "+ refus.imprimante + " " + refus.raison); 
			System.exit(1);
		}
		catch (SystemException ex)
		{
			System.err.println("CORBA::SystemException : " + ex.toString());
			ex.printStackTrace(System.out);
			System.exit(1);
		} 
	}
}


