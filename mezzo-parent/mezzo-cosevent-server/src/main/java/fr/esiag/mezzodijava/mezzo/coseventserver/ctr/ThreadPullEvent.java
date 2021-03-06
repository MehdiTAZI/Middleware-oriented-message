package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.omg.CORBA.BooleanHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;

/**
 * Class ThreadPullSupplier to ask suppliers for events
 * 
 * 
 * @author Mezzo-Team
 * 
 */
public class ThreadPullEvent implements Runnable {

    private static Logger log = LoggerFactory
	    .getLogger(ThreadPushConsumer.class);
    private ProxyForPullSupplierImpl pfps;

    /**
     * constructor for the thread pull event
     * 
     * @param proxySupplier
     *            there is one thread pull event for each proxy pull supplier
     */
    public ThreadPullEvent(ProxyForPullSupplierImpl proxySupplier) {
	log.trace("ThreadPullEvent created");
	pfps = proxySupplier;
    }

    /**
     * If the supplier is connected
     * 
     */
    @Override
    public void run() {
	log.trace("process connected suppliers");
	Event ev;
	BooleanHolder hasEvent = new BooleanHolder(true);
	int delay = Integer.valueOf(CosEventServer.properties.getProperty("eventserver.pull.delay"));
	while (pfps.getChannelCtr().getChannel()
			.getSuppliersPullConnected()
			.containsKey(pfps.getIdComponent())) {
	    try {
		ev = pfps.ask(hasEvent);
		if (hasEvent.value) {
		    pfps.getChannelCtr().addEvent(ev);
		}

	    } catch (SupplierNotFoundException e1) {
		log.debug("Supplier seems to be unreachable", e1);
		// Supplier seems to be unreachable so it's time
		// to disconnect it
		try {
		    pfps.disconnect();
		    log.debug("Supplier disconnected");
		} catch (NotConnectedException e2) {
		    log.error("Can't disconnect : Supplier not connected", e2);
		} catch (ChannelNotFoundException e3) {
		    log.error("Can't disconnect : Channel not found", e3);
		}
	    }
	    try {
		Thread.sleep(delay);
	    } catch (InterruptedException e) {
		log.error("Error, interrupted thread", e);
	    }
	}
    }
}
