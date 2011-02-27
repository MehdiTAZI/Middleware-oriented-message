package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

/**
 * Class ThreadEvent to process event in queues and send them to consumers
 *
 * In a infinite loop each 0.5s : for each subscribed consumer if it is
 * connected for each event in its list consumer.receive() if unreachable
 * consumer.disconnect() end if end for end if end for
 *
 * UC n°: US15 (+US children)
 *
 * @author Mezzo-Team
 *
 */
public class ThreadEvent implements Runnable {
    private Channel channel;

    public ThreadEvent(String topic) {
    	channel = BFFactory.getChannel(topic);
    }

    /**
     * process subscribed consumer list of the channel to find
     * ProxyForPushConsumerImpl with Event to send. If the consumer is connected
     * and it has Event in his list (in Channel), processSubscribedConsumers()
     * call <code>ppfc.receive(Event)</code> on it.
     *
     * If <code>ppfc.receive()</code> throws ConsumerUnreachableExcepetion, the
     * method disconnect the consummer calling <code>ppfc.disconnect()</code>.
     * 
     * 
     */
    public void processSubscribedConsumers() {
	// For all Subscribed Consumer to the Channel
	for (ProxyForPushConsumerImpl consumer : channel
		.getConsumersSubscribed().keySet()) {
	    // if the consumer is connected
	    System.out.println("on passe a un autre " + consumer.toString());
	    if (channel.getConsumersConnected().contains(consumer)) {
		// for all events of the consumer
		System.out.println("on passe a un autre connecte " +
		 consumer.toString() + " qui contient nb evt = "+channel.getConsumersSubscribed().get(consumer).size());
		SortedSet<Event> le = channel.getConsumersSubscribed().get(consumer);
		synchronized (le) {
		    Iterator<Event> i = le.iterator(); // Must be in
						       // synchronized
						       // block
		    while (i.hasNext()
			    && channel.getConsumersConnected().contains(
				    consumer)) {
			Event e = i.next();
			try {
			    // TODO : here manage life of the events.
			    long delta=0;//TODO remplacer ici par du COSTime
			    if(e.header.date + e.header.timestamp > new Date().getTime()+delta){
			    // send event to the consumer
				System.out.println(e.header.code+" receive appel");
				consumer.receive(e);
			    }else{
				System.out.println(e.header.code+" expire");
			    }
			    // remove event from the list
			    i.remove();

			} catch (ConsumerNotFoundException e1) {
			    // TODO log here
			    e1.printStackTrace();
			    // Consumer seems to be unreachable so it's time
			    // to disconnect it
			    try {
				consumer.disconnect();
			    } catch (NotConnectedException e2) {
			    	e2.printStackTrace();
			    } catch (NotRegisteredException e2) {
			    	e2.printStackTrace();
			    }
			    // TMA : todo => ajouter les messages non recu
			    // de la Queue
			    // Sans oublier d'ajouter dans la class qu'il
			    // faut le faite d'essayez
			    // d envoyer l ensemble des messages lors de la
			    // connexion du consumer
			}
		    }
		}
	    }

	}
    }

    /**
     * Run the thread to process events
     *
     */
    @Override
    public void run() {
	while (true) {
	    processSubscribedConsumers();
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

}
