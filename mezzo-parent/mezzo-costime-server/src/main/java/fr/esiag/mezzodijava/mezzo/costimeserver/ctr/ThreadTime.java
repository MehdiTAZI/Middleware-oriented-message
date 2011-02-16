package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;


public class ThreadTime implements Runnable{
    private TimeServiceModel model;

    public ThreadTime(TimeServiceModel model) {
	this.model=model;
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
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}
