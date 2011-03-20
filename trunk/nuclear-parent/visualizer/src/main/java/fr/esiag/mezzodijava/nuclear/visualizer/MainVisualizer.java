package fr.esiag.mezzodijava.nuclear.visualizer;

import java.util.Date;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;
import fr.esiag.nuclear.commons.model.Pression;
import fr.esiag.nuclear.commons.model.RadioActivite;
import fr.esiag.nuclear.commons.model.Temperature;

public class MainVisualizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EventClient ec = EventClient.init(args);
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic("injector system state");
			ProxyForPushConsumer consumerProxy = channelAdmin
					.getProxyForPushConsumer();
			System.out.println("creation callback");
			Callback callback = new Callback();
			CallbackConsumer cbc = ec.serveCallbackConsumer(callback);
			System.out.println("subscribe du consumer");
			consumerProxy.subscribe(cbc);
			Thread.sleep(1000);
			consumerProxy.connect();
			ORB orb = ec.getOrb();
			orb.run();
			
		} catch (EventClientException e) {
			e.printStackTrace();
		} catch (TopicNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotRegisteredException e) {
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

	public static class Callback implements CallbackConsumerOperations {

		@Override
		public void receive(Event evt) throws ConsumerNotFoundException {
			
			 if(evt.body.type.equals("String")){
				 System.out.println("MOROCCO --> IN STRING");
				 System.out.println("priority:"+evt.header.priority+",date:"+(new Date(evt.header.creationdate)).toGMTString()+" content:"+evt.body.content.extract_string());
				    }
			
			if(evt.body.type.equals("Temperature")){
				System.out.println("MOROCCO --> IN TEMPERATURE");
				Temperature t=(Temperature)evt.body.content.extract_Value();
				System.out.println("priority:"+evt.header.priority+",date:"+(new Date(evt.header.creationdate)).toGMTString()
						+" content:"+ t.getValue() +"|"+t.getUnite());
						
					
			}
			  if(evt.body.type.equals("Pression")){
			    	System.out.println("IN Pression");
					Pression t=(Pression)evt.body.content.extract_Value();
					System.out.println("priority:"+evt.header.priority+",date:"+(new Date(evt.header.creationdate)).toGMTString()
							+ ", contenu: Pression" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
						
				}
			    
			    if(evt.body.type.equals("RadioActivite")){
			    	System.out.println("IN RADIOACTIVITE");
					RadioActivite t=(RadioActivite)evt.body.content.extract_Value();
					System.out.println("priority:"+evt.header.priority+",date:"+(new Date(evt.header.creationdate)).toGMTString()
							+ ", contenu: RadioActivite" + " Value: "+t.getValue() +"| Unite: "+t.getUnite());
						
				}
			

		}
	}

}
