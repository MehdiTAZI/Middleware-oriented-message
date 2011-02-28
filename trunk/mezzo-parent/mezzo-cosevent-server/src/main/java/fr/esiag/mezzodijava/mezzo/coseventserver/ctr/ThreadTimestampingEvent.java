package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Date;
import java.util.SortedSet;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class ThreadTimestampingEvent implements Runnable{
	private SortedSet<Event> queue;
	private Date date;
	
	/**
     * Run the thread to timestamp events
     *
     */
	@Override
	public void run() {
		Event[] tmp=queue.toArray(new Event[queue.size()]);
		for (int i = 0; i < tmp.length; i++) {
			if(tmp[i].header.date  <= date.getTime() && tmp[i].header.date  >= date.getTime()+20){
				queue.remove(tmp[i]);
				tmp[i].header.date=date.getTime();
				queue.add(tmp[i]);
			}
		}
		
	}

	/**
	 * Set the SortedSet queue
	 *
	 * @param queue
	 * 			a sortedset of Events
	 *
	 */
	public void setQueue(SortedSet<Event> queue){
		this.queue=queue;
	}

	/**
	 * Set the date
	 *
	 * @param date
	 * 			the date from COS Time
	 *
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
