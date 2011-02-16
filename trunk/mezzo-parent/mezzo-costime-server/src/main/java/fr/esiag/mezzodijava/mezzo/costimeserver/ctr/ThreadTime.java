package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Date;

import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;


public class ThreadTime implements Runnable{
    private TimeServiceModel model;

    public ThreadTime(TimeServiceModel model) {
	this.model=model;
    }

    public void synchronizeComponent() {
	for (Synchronizable component : model.getComponentSubscribed()) {
		component.date(new Date(2001,12,14).getTime());
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
