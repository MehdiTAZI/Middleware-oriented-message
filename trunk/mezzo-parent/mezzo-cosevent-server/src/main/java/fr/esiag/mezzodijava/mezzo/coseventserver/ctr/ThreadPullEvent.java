package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.SupplierNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;

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
		boolean trouve = true;
		Event ev;
		while (trouve
				&& pfps.getChannelCtr().getChannel()
						.getSuppliersPullConnected()
						.containsKey(pfps.getIdComponent())) {
			try {
				ev = pfps.ask();
				if (ev == null) {
					trouve = false;
				} else {
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
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error("Error, interrupted thread", e);
			}
		}
	}
}
