package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public class ThreadPullEvent implements Runnable{
	
	private static Logger log = LoggerFactory.getLogger(ThreadEvent.class);
    private Channel channel;
    private EventServer es = EventServer.getInstance();
    
    public ThreadPullEvent(String topic) {
	log.trace("ThreadPullEvent created");
	channel = es.getChannel(topic);
    }

    public void processConnectedSupplier() {
    	log.trace("process connected supplier");
    	//ProxyForPullSupplier pfps
		//Event e = pfps.ask();	
    	//channel.addEvent(em);
	    }

    /**
     * Run the thread to process events
     * 
     */
    @Override
    public void run() {
	while (true) {
	    processConnectedSupplier();
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}
