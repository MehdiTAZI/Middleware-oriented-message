package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import java.util.List;

import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;

public interface DbEventConnector{
	public boolean persist(EventInfo e);//if the event exist so update it ,else insert it;just call the persist method of JPA
	public EventInfo find(String type);//return the Event from the type given in param 
	public boolean exist(EventInfo e);//check if the event exist ( compare all the columns )
	public List<EventInfo> findLastFive(String type);//return the 5 last Events from the type given in param 
}
