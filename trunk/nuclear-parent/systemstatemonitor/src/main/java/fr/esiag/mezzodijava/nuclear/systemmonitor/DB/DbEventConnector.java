package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfoPK;

public interface DbEventConnector{
	//the primary key of the message are the CODE and the TYPE
	public boolean persist(EventInfo e);//if the event exist so update it ,else insert it;just call the persist method of JPA
	public EventInfo find(EventInfoPK pk);//return the Event from the primary key given in params 
	public boolean exist(EventInfo e);//check if the event exist ( compare all the columns )
}
