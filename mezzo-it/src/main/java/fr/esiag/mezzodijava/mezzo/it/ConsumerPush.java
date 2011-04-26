package fr.esiag.mezzodijava.mezzo.it;

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
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class ConsumerPush {
	
	public static void main(String[] args) throws EventClientException, TopicNotFoundException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, InterruptedException, NotConnectedException {
		
			EventClient ec = EventClient.init(args);
			String channelName; 
			String idc;
			if (args.length == 2) {
			    channelName = args[0];
			    idc = args[1];
			}else{
				channelName = "injector system state";
				idc = "moroccopush";
			}
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic(channelName);
			String idcomp = idc;
			CallBackConsumerImpl cbci = new CallBackConsumerImpl();
			CallbackConsumer cbc = ec.serveCallbackConsumer(cbci);
			ProxyForPushConsumer consumerProxy = channelAdmin
					.getProxyForPushConsumer(idcomp);
			System.out.println("Subscribe du consumer "+idc);
			try {
				consumerProxy.subscribe();
			} catch (AlreadyRegisteredException e1) {
				System.out.println("fail subscribe");
				e1.printStackTrace();
			}
			System.out.println("connexion du consumer "+idc);
			Thread.sleep(500);
			try {
				consumerProxy.connect(cbc);
			} catch (NotRegisteredException e) {
				System.out.println("fail connexion");
				e.printStackTrace();
			}
			ec.getOrb().run();
    }
	
	public static class CallBackConsumerImpl implements
    CallbackConsumerOperations {
		int n = 0;
		public CallBackConsumerImpl() {}
		
		@Override
		public void receive(Event evt) throws ConsumerNotFoundException {
			if (n==0){
				System.out.println("Event number "+n+" received :");
				System.out.println("Type: "+evt.body.type+", contenu: "+evt.body.content);
				System.out.println(evt.body.content.extract_string());
			}else{
				System.out.println("Reception du n°"+n);
			}
			n++;
		}
}
}
