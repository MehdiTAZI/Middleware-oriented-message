package com.esiag.server;

import org.omg.CORBA.*; // pour Holder des types de base
import java.net.*;
import java.io.*;

class ImprimanteImpl extends ImprimantePOA
{ 
	private short tacheCourante;
	private String nom=new String(""); 

	ImprimanteImpl(String nomImpr)
	{
		nom=new String(nomImpr);
		System.out.println("Instantiation de l'imprimante :  "+ nomImpr);
	}

	public void getInfo(StringHolder imprimante, StringHolder serveur, StringHolder machine, ShortHolder tacheEnCours)
	{
		imprimante.value=nom;
		serveur.value="ServeurImprimantes";
		
		InetAddress maMachine;
		try
		{
		
			maMachine=InetAddress.getLocalHost();
			machine.value=maMachine.getHostName();
			
		}
		catch (UnknownHostException u)
		{
			machine.value=new String("machine inconnue");
		}
		tacheEnCours.value=tacheCourante;
	}

	public short printDocument (String n, IntHolder t) throws TacheRefusee
	{ 
		// ouverture du fichier 
		// recupere taille du fichier
		File f=new File(n);
		if ( ! f.isFile() )
		{ 
			System.out.println(nom+" impression impossible: "+n+" ne peut pas ?tre ouvert");
			throw new TacheRefusee(nom,n+" : ne peut pas ?tre ouvert");
		} 
		t.value = (int)f.length();
		if (tacheCourante > 2)
		{ 
			System.out.println("Je suis d?finitivement surcharg?e, il faut me red?marrer !");
			throw new TacheRefusee(nom,"surcharge"); 
		}
		System.out.println("Impression du document : "+ n + " sur " + nom + " taille " + t.value);
		tacheCourante++;
		return tacheCourante; 
	}
}


