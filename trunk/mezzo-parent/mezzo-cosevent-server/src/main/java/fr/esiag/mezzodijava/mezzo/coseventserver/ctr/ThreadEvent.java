package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDAO;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

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
@Deprecated
public class ThreadEvent implements Runnable {
    private static Logger log = LoggerFactory.getLogger(ThreadEvent.class);
    private Channel channel;
    private EventServer es = EventServer.getInstance();

    private JdbcDAO dao = DAOFactory.getJdbcDAO();
    
    /**
     * constructor for the Thread Event
     * @param topic the thread Event will be attached to this topic
     */
    public ThreadEvent(String topic) {
	log.trace("ThreadEvent created");
	channel = es.getChannel(topic);
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
	log.trace("process subscribed consumers");
	// For all Subscribed Consumer to the Channel
	for (ConsumerModel consumer : channel.getConsumers().values()) {
	    ProxyForPushConsumerImpl pfpc;
	    // if the consumer is connected
	    pfpc = channel.getConsumersConnected()
		    .get(consumer.getIdConsumer());
	    if (pfpc != null) {
		// for all events of the consumer
		SortedSet<EventModel> le = consumer.getEvents();
		synchronized (le) {
		    Iterator<EventModel> i = le.iterator();
		    // Must be in
		    // synchronized
		    // block
		    while (i.hasNext()) {
			EventModel em = i.next();
			try {
			    long delta = CosEventServer.getDelta();
			    if (em.getCreationdate() + em.getTimetolive() > new Date()
				    .getTime() + delta) {
				// send event to the consumer
				Event e = new EventConvertor()
					.transformToEvent(em);
				pfpc.receive(e);
				
			    } else {
				log.debug("Event de type " + em.getType()
					+ " expire");
			    }
			    // remove event from the list
			    i.remove();
			    dao.deleteEventByConsumer(consumer.getId(), em.getId());
			} catch (ConsumerNotFoundException e1) {
			    log.debug("Consumer seems to be unreachable", e1);
			    // Consumer seems to be unreachable so it's time
			    // to disconnect it
			    try {
				pfpc.disconnect();
				log.debug("Consumer disconnected");
			    } catch (NotConnectedException e2) {
				log.error(
					"Can't disconnect : Consumer not connected",
					e2);
			    } catch (NotRegisteredException e2) {
				log.error(
					"Can't disconnect : Consumer not registered",
					e2);
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
		log.error("thread event interrupted",e);
	    }
	}

    }

}
