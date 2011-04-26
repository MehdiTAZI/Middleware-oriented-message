package fr.esiag.mezzodijava.mezzo.it;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class SupplierPush {
	
	private static ORB orb;
	
	public static void main(String[] args) {
		try {
			EventClient ec = EventClient.init(args);
			//orb = ec.getOrb();
			String channelName; 
			String idc;
			if (args.length == 2) {
			    channelName = args[0];
			    idc = args[1];
			}else{
				channelName = "injector system state";
				idc = "supplierpush";
			}
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic(channelName);
			String idcomp = idc;
			ProxyForPushSupplier supplierProxy = channelAdmin
		    .getProxyForPushSupplier(idcomp);
			System.out.println("connexion du supplier");
			supplierProxy.connect();
			Thread.sleep(5000);
			for(int n = 0;n<10;n++){
				String toSend = new String( "Message number "+n+": trolololo");
				try {
					supplierProxy.push(EventFactory.createEventString(n, 600, toSend));
				} catch (NotConnectedException e) {
					System.out.println("Fail connection");
					e.printStackTrace();
				}
			}
			
		} catch (EventClientException e) {
			e.printStackTrace();
		} catch (TopicNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	

}
