package fr.esiag.mezzodijava.mezzoproto.consumer.main;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplier;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushConsumerHelper;
import fr.esiag.mezzodijava.mezzoproto.libclient.impl.ChannelManagerImpl;
import fr.esiag.mezzodijava.mezzoproto.libclient.impl.PushConsumerImpl;

public class MainConsumerMorocco extends Thread{
	
	private String[] args;

	public MainConsumerMorocco(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) {
		(new MainConsumerMorocco(args)).start();
	}
	
	public void run() {
		try {
			
		String channelName="MEZZO-DI-JAVA-2";
		String consumerName="MOROCCO";
		
		ORB orb=ORB.init(args, null);
		PushConsumerImpl pc=new PushConsumerImpl(consumerName);
		ChannelManagerImpl ec=new ChannelManagerImpl(orb);
		
		ProxyPushSupplier ppc=ec.get_Proxy_Push_Supplier(channelName);
		
		POA poa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		poa.the_POAManager().activate();												
		ppc.connect(PushConsumerHelper.narrow(poa.servant_to_reference(pc)));		
		System.out.println("le consumer est connecte au canal "+channelName);
		
		
		int nb=0;
		while(nb!=1){
			Thread.sleep(30000);
			nb=1;
		}
	
		ppc.disconnect();
		System.out.println("le consumer est disconnected");		
		
		} catch (Exception e) {
			System.out.println("Erreur : "+e.getMessage());
		}
}	
}
