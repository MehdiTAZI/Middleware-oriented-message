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
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

/**
 * Class ThreadPushConsumer to process event in queues and send them to consumers
 * 
 * In a infinite loop each 0.5s : for the provided consumer if it is
 * connected for each event in its list consumer.receive() if unreachable
 * consumer.disconnect() end if end for end if end for
 * 
 * UC n°: US15 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */
public class ThreadPushConsumer implements Runnable {
    private static Logger log = LoggerFactory
	    .getLogger(ThreadPushConsumer.class);
    private ProxyForPushConsumerImpl pfpc;
    private ConsumerModel consumer;

    private JdbcDAO dao = DAOFactory.getJdbcDAO();

    public ThreadPushConsumer(ProxyForPushConsumerImpl proxyConsumer) {
	log.trace("ThreadEvent created");
	pfpc = proxyConsumer;
	consumer = pfpc.getChannelCtr().getChannel().getConsumers().get(proxyConsumer.getIdComponent());
	// channel = es.getChannel(topic);
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


    }
    /**
     * If the consumer is connected
     * and it has Event in his list (in Channel), processSubscribedConsumers()
     * call <code>ppfc.receive(Event)</code> on it.
     * 
     * If <code>ppfc.receive()</code> throws ConsumerUnreachableExcepetion, the
     * method disconnect the consummer calling <code>ppfc.disconnect()</code>.
     * 
     * 
     */
    @Override
    public void run() {
	log.trace("process subscribed consumers");
	while (consumer.getChannel().getConsumersConnected()
		.containsKey(consumer.getIdConsumer())) {
	    // for all events of the consumer
	    SortedSet<EventModel> le = consumer.getEvents();
	    synchronized (le) {
		Iterator<EventModel> i = le.iterator();
		// Must be in
		// synchronized
		// block
		while (i.hasNext() && pfpc != null) {
		    EventModel em = i.next();
		    try {
			long delta = CosEventServer.getDelta();
			if (em.getCreationdate() + em.getTimetolive() > new Date()
				.getTime() + delta) {
			    // send event to the consumer
			    Event e = new EventConvertor().transformToEvent(em);
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
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

}
