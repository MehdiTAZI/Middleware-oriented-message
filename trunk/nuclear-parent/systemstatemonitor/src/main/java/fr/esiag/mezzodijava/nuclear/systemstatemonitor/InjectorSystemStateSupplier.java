package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;
import fr.esiag.nuclear.commons.model.Pression;
import fr.esiag.nuclear.commons.model.RadioActivite;
import fr.esiag.nuclear.commons.model.Temperature;

public class InjectorSystemStateSupplier {
	
	private ProxyForPushSupplier supplierProxy;
	
	public InjectorSystemStateSupplier() throws EventClientException,
	TopicNotFoundException, ChannelNotFoundException,
	AlreadyRegisteredException {
		EventClient ec = EventClient.init(null);
		ChannelAdmin channelAdmin = ec.resolveChannelByTopic("injector system state");
		supplierProxy = channelAdmin
		.getProxyForPushSupplier();
		
		try {
			supplierProxy.connect();
			System.out.println("supplier injector connecté");
		} catch (MaximalConnectionReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ALL DONE for supplier injector");
		/*ORB orb = BFFactory.createOrb(null, null);
		orb.run();*/
	}

	public void PushEvent(Event e)
	{
		try {
			supplierProxy.push(e);
			
			if(e.body.type.equals("String")){	
				System.out.println("IN STRING");
			System.out.println("!!ALERTE envoyee : Code("+e.header.code+"), Timestamp:" + e.header.creationdate
					+ ", contenu " + e.body.content.extract_string());
			}
			if(e.body.type.equals("Temperature")){
				System.out.println("PUSH EVENT");
				Temperature t=(Temperature)e.body.content.extract_Value();
				System.out.println("!!ALERTE envoyee : Code("+e.header.code+"), Timestamp:" + e.header.creationdate
						+ ", contenu " + t.getValue() +"/"+t.getUnite());
					
			}
			
			  if(e.body.type.equals("Pression")){
			    	System.out.println("IN Pression");
					Pression t=(Pression)e.body.content.extract_Value();
					System.out.println("!!ALERTE envoyee : Code("+e.header.code+"), Timestamp:" + e.header.creationdate
							+ ", contenu: Pression" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
						
				}
			    
			    if(e.body.type.equals("RadioActivite")){
			    	System.out.println("IN RADIOACTIVITE");
					RadioActivite t=(RadioActivite)e.body.content.extract_Value();
					System.out.println("!!ALERTE envoyee : Code("+e.header.code+"), Timestamp:" + e.header.creationdate
							+ ", contenu: RadioActivite" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
						
				}
			    
			
		} catch (ChannelNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotConnectedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
