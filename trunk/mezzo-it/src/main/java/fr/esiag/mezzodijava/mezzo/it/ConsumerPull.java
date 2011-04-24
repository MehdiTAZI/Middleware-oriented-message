package fr.esiag.mezzodijava.mezzo.it;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.EventClient;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class ConsumerPull {
	
	private static List<Event> messagesRecu = Collections
	.synchronizedList(new ArrayList<Event>());
	
	private static ORB orb;
	
	public static void main(String[] args) throws EventClientException, TopicNotFoundException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, InterruptedException, NotConnectedException {
		
			// mise blanc de la liste des messages reçus
			messagesRecu = Collections.synchronizedList(new ArrayList<Event>());
			EventClient ec = EventClient.init(args);
			//orb = ec.getOrb();
			String channelName; 
			String idc;
			if (args.length == 2) {
			    channelName = args[0];
			    idc = args[1];
			}else{
				channelName = "injector system state";
				idc = "moroccopull";
			}
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic(channelName);
			String idcomp = idc;
			ProxyForPullConsumer consumerProxy = channelAdmin
					.getProxyForPullConsumer(idcomp);
			System.out.println("connexion du consumer");
			consumerProxy.connect();
			Thread.sleep(20000);
			// begin of pull consumer 
    	    Event ev;
    	    BooleanHolder hasEvent = new BooleanHolder(true);
    	    while(hasEvent.value)
    	    {
    	    	System.out.println("pull consumer");
    	    	ev = consumerProxy.pull(hasEvent);
    	    	if (hasEvent.value){
    	    		System.out.println("event reçu de type: "+ev.body.type+", contenu: "+ev.body.content);
    	    		messagesRecu.add(ev);
    	    	}
    	    }
    	    System.out.println("nb messages reçus :"+messagesRecu.size());
	}
}
