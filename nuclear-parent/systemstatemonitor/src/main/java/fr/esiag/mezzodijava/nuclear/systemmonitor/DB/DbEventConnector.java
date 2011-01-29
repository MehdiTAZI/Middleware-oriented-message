package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public interface DbEventConnector {
	//the primary key of the message are the CODE and the TYPE
	public boolean persist(Event e);//if the event exist so update it ,else insert it;just call the persist method of JPA
	public Event find(int code,String type);//return the Event from the primary key given in params 
	public boolean exist(Event e);//check if the event exist ( compare all the columns )
}
