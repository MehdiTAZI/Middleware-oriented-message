package com.esiag.server;

import org.omg.CORBA.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.*;

public class ServeurImprimantes 
{
	public static void main(String args[]) throws Exception
	{
		ORB orb =ORB.init(args, null);   		
		
		// tableau de 2 servants CORBA 
		ImprimanteImpl impl[]=new ImprimanteImpl[2];
		
		// tableau des 2 objets CORBA associ?s
		org.omg.CORBA.Object objetCORBA[]=new org.omg.CORBA.Object[2];
		String nomImpr[]=new String[2];
		
		// A completer
		
		POA rootpoa =POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		rootpoa.the_POAManager().activate();

		nomImpr[0]=new String("canon");
		nomImpr[1]=new String("hp");
		for (int i=0;i<impl.length;i++)
		{
			impl[i]=new ImprimanteImpl(nomImpr[i]);			
			objetCORBA[i] = rootpoa.servant_to_reference(impl[i]);
		}
		
		//  Ecrire dans un fichier la r?f?rence des deux objets CORBA
		for (int i=0; i<2; i++)
		{
			// A completer
			
			String nomFichier =new String(nomImpr[i]+".OR");
			BufferedWriter out=new BufferedWriter(new FileWriter(nomFichier));
			out.write(orb.object_to_string(objetCORBA[i]),0,orb.object_to_string(objetCORBA[i]).length());
			out.close();
		}
		
		// attente de requ?tes
		System.out.println("Boucle d'evenements ...");
		orb.run();
	}
}


