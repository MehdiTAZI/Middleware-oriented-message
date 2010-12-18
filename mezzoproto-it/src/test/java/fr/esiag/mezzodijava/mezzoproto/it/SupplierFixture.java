package fr.esiag.mezzodijava.mezzoproto.it;

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
import fr.esiag.mezzodijava.mezzoproto.libclient.impl.PushSupplierImpl;

//|script|supplier fixture|
public class SupplierFixture{

	private String[] args = {"-ORBInitRef", "NameService=corbaloc::127.0.0.1:1050/NameService", "-Djacorb.home=C:\\mezzodev\\jacorb-2.3.1","-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB","-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"};

	ProxyPushConsumer ppc = null;

	public SupplierFixture(){
		
	}
	//|init with suppliername|ALASKA| and topic|MEZZO-DI-JAVA-1|
	//|init with suppliername and topic;|ALASKA|MEZZO-DI-JAVA-1|
	public boolean initWithSuppliernameAndTopic(String supplierName, String channelName) {
		try {
			ORB orb = ORB.init(args, null);

			PushSupplierImpl ps = new PushSupplierImpl(supplierName);

			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));

			ChannelManager ec = ChannelManagerHelper.narrow(nc
					.resolve_str("ChannelManager"));

			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			this.ppc = ec.get_Proxy_Push_Consumer(channelName);

			ppc.connect(PushSupplierHelper.narrow(poa.servant_to_reference(ps)));
			System.out.println("le supplier est connecte au canal "
					+ channelName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (ppc!=null);
	}

	// |push|123 Pression 600 hPa|
	// |push|243 Pression alerte 2000 hPa| 
	// |push|345  Pression 1012 hPa|
	// |push|652  Temperature alerte 100° c|
	// |push|831  Temperature 10° c| 
	// |push|983  Temperature 43° c| 
	// |push|836  Radioactivité 20 Bq| 
	// |push|909  Radioactivité 60 Bq| 
	// |push|123  Radioactivité alerte 100 Bq|
	public boolean push(String message) {
		System.out.println("push");
		Event event = new Event();
		event.message = message;
		ppc.push(event);
		return true;
	}
	
	// |disconnect|
	public boolean disconnect() {
	ppc.disconnect();
	return true;
	}
}
