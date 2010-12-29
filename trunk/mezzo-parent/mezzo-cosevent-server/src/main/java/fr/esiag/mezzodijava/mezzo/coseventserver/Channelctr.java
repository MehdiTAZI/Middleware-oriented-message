package fr.esiag.mezzodijava.mezzo.coseventserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import fr.esiag.mezzodijava.mezzo.cosevent.Consumer;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Supplier;

public class Channelctr {
	private String topic;
	private int capacity;
	// TODO : not consumer / supplier sont les parents de PPC / PPS
	private Map<Consumer, Queue<Event>> consumers;
	private List<Supplier> suppliers;
	private List<Event> events;
	
	public Channelctr(String topic, int capacity) {
		this.topic=topic;
		this.capacity=capacity;
		consumers = new HashMap<Consumer,Queue<Event>>();
		suppliers = new ArrayList<Supplier>();
	}
	
	public void addConsumer(Consumer consumer)throws MaximalConnectionReachedException{
		if (consumersFull())
			throw new MaximalConnectionReachedException("capacity is: "+ capacity);
		// priorityQueue = fifo
		// offer() = insert  
		// poll() = remove
		consumers.put(consumer, new PriorityQueue<Event>());
	}
	public void addSupplier(Supplier supplier) throws MaximalConnectionReachedException{
		if (suppliersFull())
			throw new MaximalConnectionReachedException("capacity is: "+ capacity);
		suppliers.add(supplier);
	}
	
	public void processEvents(){
		
		
	}
	
	// TODO : hypothèse sur problème de concurence
	public boolean consumersFull(){
		return (capacity == consumers.size());
	}
	public boolean suppliersFull(){
		return (capacity == suppliers.size());
	}
	
	public String getTopic(){
		return topic;
	}
	public int getCapacity(){
		return capacity;
	}

}
