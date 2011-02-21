package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;


public class ThreadTime implements Runnable{
    private TimeServiceModel model;
    private long timeSpan = 1000;


	public ThreadTime(TimeServiceModel model) {
	this.model=model;
    }
	
    public ThreadTime(TimeServiceModel model, long timeSpan) {
		super();
		this.model = model;
		this.timeSpan = timeSpan;
	}

	public void synchronizeComponent() {
	for (Synchronizable component : model.getComponentSubscribed()) {
		Date date=new Date();
		component.date(date.getTime());
	 }
    }
    @Override
    public void run() {
	while (true) {
	    synchronizeComponent();
	    try {
		Thread.sleep(this.timeSpan);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
    
    public long getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(long timeSpan) {
		this.timeSpan = timeSpan;
	}
}
