package fr.esiag.mezzodijava.mezzoproto.supplier.main;

import java.util.Vector;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelManager;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelManagerHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumer;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushSupplierHelper;
import fr.esiag.mezzodijava.mezzoproto.capteur.Capteur;
import fr.esiag.mezzodijava.mezzoproto.capteur.XMLFile;
import fr.esiag.mezzodijava.mezzoproto.libclient.impl.PushSupplierImpl;

public class MainSupplierSudan extends Thread {

	private Capteur capteur;
	private String[] args;

	public MainSupplierSudan(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) {
		(new MainSupplierSudan(args)).start();
	}

	public void run() {

		try {

			String supplierName = "SUDAN";
			String channelName = "MEZZO-DI-JAVA-3";
			ORB orb = ORB.init(args, null);
									
			
			PushSupplierImpl ps = new PushSupplierImpl(supplierName);

			
			
			NamingContextExt nc=NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			
			
			
			ChannelManager ec=ChannelManagerHelper.narrow(nc.resolve_str("ChannelManager"));
			
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			ProxyPushConsumer ppc = ec.get_Proxy_Push_Consumer(channelName);
			

			ppc.connect(PushSupplierHelper.narrow(poa.servant_to_reference(ps)));
			System.out.println("le supplier est connecte au canal "
					+ channelName);

			capteur = new Capteur(new XMLFile());
			Vector<String> data = capteur.getData();

			int nb = 0;
			while (true) {
				Event event = new Event();
				event.message = data.get(nb++);
				ppc.push(event);
				Thread.sleep(1000);
				if (nb == 6)
					nb = 0;
			}

		} catch (Exception e) {
			//throw new RuntimeException(e);
			System.out.println("erreur : " + e.getMessage());
		}

	}
}
