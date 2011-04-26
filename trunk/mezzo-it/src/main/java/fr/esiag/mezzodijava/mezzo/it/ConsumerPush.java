package fr.esiag.mezzodijava.mezzo.it;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumerOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplierOperations;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.it.TestCOSEventIT.CallBackConsumerImpl;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class ConsumerPush {
	
	private static List<Event> messagesRecu = Collections
	.synchronizedList(new ArrayList<Event>());
	
	private static ORB orb;
	
	public static void main(String[] args) throws EventClientException, TopicNotFoundException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, InterruptedException, NotConnectedException {
		
			// mise blanc de la liste des messages reçus
			EventClient ec = EventClient.init(args);
			//orb = ec.getOrb();
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
			System.out.println("Creation callBack");
			CallBackConsumerImpl cbci = new CallBackConsumerImpl();
			CallbackConsumer cbc = ec.serveCallbackConsumer(cbci);
			ProxyForPushConsumer consumerProxy = channelAdmin
					.getProxyForPushConsumer(idcomp);
			System.out.println("Subscribe du consumer");
			try {
				consumerProxy.subscribe();
			} catch (AlreadyRegisteredException e1) {
				System.out.println("fail subscribe");
				e1.printStackTrace();
			}
			System.out.println("connexion du consumer");
			try {
				consumerProxy.connect(cbc);
			} catch (NotRegisteredException e) {
				System.out.println("fail connexion");
				e.printStackTrace();
			}
			
			Thread.sleep(50000);
	}
	public static class CallBackConsumerImpl implements
    CallbackConsumerOperations {
		int n = 0;
		public CallBackConsumerImpl() {
			
		}
		@Override
		public void receive(Event evt) throws ConsumerNotFoundException {
			System.out.println("Event number "+n+" received :");
			System.out.println("Type: "+evt.body.type+", contenu: "+evt.body.content);
			System.out.println(evt.body.content.extract_string());
			n++;
			
			
		}
}
}
