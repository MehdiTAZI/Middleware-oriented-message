package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

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

public class ThreadTime implements Runnable{
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
	this.model=model;
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
	for (Synchronizable component : model.getComponentSubscribed()) {
		Date date=new Date();
		component.date(date.getTime());
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
    			log.info("Slept {}",timeSpan);
    			Thread.sleep(this.timeSpan);
    		} catch (InterruptedException e) {
    		log.error("Error in the thread", e);
    		}
	}
    }
}
