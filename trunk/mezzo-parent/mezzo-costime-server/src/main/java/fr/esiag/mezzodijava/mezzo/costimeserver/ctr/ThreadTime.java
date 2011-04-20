package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

/**
 * ThreadTime : thread to give time to consumers
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 * 
 */

public class ThreadTime implements Runnable {
    private static Logger log = LoggerFactory.getLogger(ThreadTime.class);
    private Synchronizable cc;
    private TimeServiceModel model;
    private long timeSpan;

    /**
     * Constructor for a ThreadTime
     * 
     * @param model
     *            TimeServiceModel
     */
    public ThreadTime(Synchronizable cc, TimeServiceModel model, long timeSpan) {
	super();
	this.cc = cc;
	this.model = model;
	this.timeSpan = timeSpan;
    }

    /**
     * To fill date and time to a synchronize component
     * 
     */
    @Override
    public void run() {
	log.info("Initialize thread");
	while (model.getComponentSubscribed().contains(cc)) {
	    try {
		cc.date(System.currentTimeMillis());
		try {
		    Thread.sleep(timeSpan);
		} catch (InterruptedException e) {
		    log.error("time thread interrupted for : " + cc);
		}
	    } catch (org.omg.CORBA.SystemException ex) {
		model.getComponentSubscribed().remove(cc);
		log.warn("Component unreachable unsubscribed : " + cc);
		break;
	    }
	}
    }
}
