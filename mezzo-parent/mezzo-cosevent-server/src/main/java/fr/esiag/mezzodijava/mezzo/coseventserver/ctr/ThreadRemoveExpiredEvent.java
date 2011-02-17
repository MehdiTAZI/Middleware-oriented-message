package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Date;
import java.util.PriorityQueue;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class ThreadRemoveExpiredEvent implements Runnable{

	private PriorityQueue<Event> queue;
	private Date date;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	public void setQueue(PriorityQueue<Event> queue){
		this.queue=queue;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
