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
	
	public static void main(String[] args) throws EventClientException, TopicNotFoundException, ChannelNotFoundException, MaximalConnectionReachedException, AlreadyConnectedException, InterruptedException, NotConnectedException {
		
			// mise blanc de la liste des messages reçus
			messagesRecu = Collections.synchronizedList(new ArrayList<Event>());
			EventClient ec = EventClient.init(args);
			ChannelAdmin channelAdmin = ec.resolveChannelByTopic("injector system state");
			String idcomp = "moroccopull";
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
    	    	System.out.println(hasEvent.value);
    	    	if (hasEvent.value){
    	    		System.out.println("add_event");
    	    		messagesRecu.add(ev);
    	    	}
    	    }
    	    System.out.println("nb messages reçus :"+messagesRecu.size());
			ORB orb = ec.getOrb();
			orb.run();
	}
}
