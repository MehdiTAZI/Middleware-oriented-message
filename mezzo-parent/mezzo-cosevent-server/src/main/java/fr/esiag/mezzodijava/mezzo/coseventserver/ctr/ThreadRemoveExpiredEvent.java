package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import javassist.expr.NewArray;
import org.apache.commons.collections.list.SynchronizedList;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;

/**
 * Class ThreadRemoveExpiredEvent to find expired event in queues 
 * and detroy them if it is expired
 *
 *
 * UC n°: FURPS CI14
 *
 * @author Mezzo-Team
 *
 */
public class ThreadRemoveExpiredEvent implements Runnable{

	private PriorityQueue<Event> queue;
	private Date date;
	private List<Event> tmp=new ArrayList<Event>();
	
	
	/**
	 * Fill the properties queue ans date
	 *
	 * @param queue
	 *            a priority queue of events
	 * @param date
	 *            the COS Time date        
	 */
	public ThreadRemoveExpiredEvent(PriorityQueue<Event> queue,Date date ) {
		// TODO Auto-generated constructor stub
		this.queue=queue;
		this.date=date;
	}
	
	@Override
	public void run() {
		while(true){
			System.out.println("IN THREAD");
			System.out.println("size "+ queue.size());
			synchronized (this) {
				for (Event e: queue) {					
					System.out.println("evt --> "+ e.header.date +" "+ e.header.timestamp + " == " + new Date().getTime());
					long delta=new Date().getTime()-date.getTime();
					if(e.header.date + e.header.timestamp < new Date().getTime()+delta){
						System.out.println("REMOVE "+e.body.content );
						//queue.remove(e);
						tmp.add(e);
					}				
				}	
				try {
					removeEvents(tmp);
					Thread.sleep(800);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}
	}

	/**
	 * Remove from the queue the events of the list in param
	 *
	 * @param tmp
	 *            A list of events to remove
	 */
	public void removeEvents(List<Event> tmp){
		synchronized (queue) {
			for(Event e: tmp)
				queue.remove(e);	
		}
		
	}
	
	/**
	 * Set the priority queue
	 *
	 * @param queue
	 * 			a priorityQueue of Events
	 *
	 */
	public void setQueue(PriorityQueue<Event> queue){
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
