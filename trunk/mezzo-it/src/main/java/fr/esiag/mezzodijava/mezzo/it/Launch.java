package fr.esiag.mezzodijava.mezzo.it;

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

public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EventClient ec = EventClient.init(args);
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic("nuclear sensor");
			ProxyForPushConsumer consumerProxy = channelAdmin
					.getProxyForPushConsumer();
			System.out.println("creation callback");
			CallbackNotConnected callback = new CallbackNotConnected();
			CallbackConsumer cbc = ec.serveCallbackConsumer(callback);
			System.out.println("subscribe du consumer");
			consumerProxy.subscribe(cbc);
			Thread.sleep(10000);
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
		} catch (InterruptedException e) {
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
		}
		

	}

	public static class CallbackNotConnected implements CallbackConsumerOperations {

		@Override
		public void receive(Event evt) throws ConsumerNotFoundException {
			System.out.println("priority:"+evt.header.priority+",date:"+(new Date(evt.header.date)).toGMTString()+" content:"+evt.body.content);

		}

	}
}
