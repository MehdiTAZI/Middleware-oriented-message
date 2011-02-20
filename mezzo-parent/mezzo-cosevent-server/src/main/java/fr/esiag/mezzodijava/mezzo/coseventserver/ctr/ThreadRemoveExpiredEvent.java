package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

import javassist.expr.NewArray;

import org.apache.commons.collections.list.SynchronizedList;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class ThreadRemoveExpiredEvent implements Runnable{

	private SortedSet<Event> queue;
	private Date date;
	private List<Event> tmp=new ArrayList<Event>();
	
	
	public ThreadRemoveExpiredEvent(SortedSet<Event> queue,Date date ) {
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
					if(e.header.date + e.header.timestamp < new Date().getTime()){
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

	public void removeEvents(List<Event> tmp){
		synchronized (queue) {
			for(Event e: tmp)
				queue.remove(e);	
		}
		
	}
	
	public void setQueue(SortedSet<Event> queue){
		this.queue=queue;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
