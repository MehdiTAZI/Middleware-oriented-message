package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

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
    private TimeServiceModel model;
    private long timeSpan = 1000;

    /**
     * Constructor for a ThreadTime
     * 
     * @param model
     *            TimeServiceModel
     */
    public ThreadTime(TimeServiceModel model) {
	this.model = model;
    }

    /**
     * Constructor for a ThreadTime
     * 
     * @param model
     *            TimeServiceModel
     * @param timeSpan
     * 
     */
    public ThreadTime(TimeServiceModel model, long timeSpan) {
	super();
	this.model = model;
	this.timeSpan = timeSpan;
    }

    /**
     * To fill date and time to a synchronize component
     * 
     */
    public void synchronizeComponent() {
	Set<Synchronizable> components = model.getComponentSubscribed();
	synchronized(components){
	    Iterator<Synchronizable> i = components.iterator();
	while(i.hasNext()){
	    Date date = new Date();
	    Synchronizable component = i.next();
	    try {
		component.date(date.getTime());
	    } catch (org.omg.CORBA.SystemException ex) {
		i.remove();
		log.warn("Component unreachable unsubscribed : " + component);
	    }
	}
	}
    }

    /**
     * Run the thread to synchronize components
     * 
     */
    @Override
    public void run() {
	log.info("Initialize thread");
	while (true) {
	    synchronizeComponent();
	    try {
		log.trace("Slept {}", timeSpan);
		Thread.sleep(this.timeSpan);
	    } catch (InterruptedException e) {
		log.error("Error in the thread", e);
	    }
	}
    }
}
