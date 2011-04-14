package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

public class ConsumerModel {

    private static final long serialVersionUID = 1L;

    private int id;
    
    private Channel channel;

    private String idConsumer;

    private SortedSet<EventModel> eventsInQueue = Collections
	    .synchronizedSortedSet(new TreeSet<EventModel>(new PriorityEventModelComparator()));

    /**
     * Getter of the Id
     * @return
     */
    public int getId() {
	return id;
    }

    /**
     * Set the Id
     * @param id
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Getter of the IdConsumer
     * @return
     */
    public String getIdConsumer() {
	return idConsumer;
    }

    /**
     * Set the IdConsumer
     * @param idConsumer
     */
    public void setIdConsumer(String idConsumer) {
	this.idConsumer = idConsumer;
    }

    /**
     * Getter of the events associated to the consumer
     * @return
     */
    public SortedSet<EventModel> getEvents() {
	return eventsInQueue;
    }

    /**
     * Set the Events of the Consumer
     * @param events
     */
    public void setEvents(SortedSet<EventModel> events) {
	this.eventsInQueue = events;
    }

    /**
     * Getter of the channel's consumer
     * @return
     */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Set the channel associated to the consumer
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public EventModel getFirstFromQueue(){
		EventModel em;
		try{
			em = eventsInQueue.first();
		}catch (NoSuchElementException e){
			return null;
		}
		eventsInQueue.remove(eventsInQueue.first());
		
		return em;
	}
}
