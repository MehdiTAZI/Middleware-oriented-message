package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

public interface JdbcDAO {
	
	/* methodes de selection */
	public List<Channel> findAllChannel();
	public SortedSet<EventModel> findEventByChannel (int channelId);
	public Map<String,ConsumerModel> findConsumerByChannel (int channelId);
	public SortedSet<EventModel> findEventByConsumer (int idConsumer);
	
	/* methodes d'insertion */
	
	public int insertChannel (Channel channel);
	public int insertConsumer (ConsumerModel consumer);
	public int insertEvent (EventModel event);
	
	/* methodes de mise à jour */
	
	public int updateChannel (int channelId);
	
	/* methodes de suppression */
	
	public boolean deleteEventByConsumer(int consumerId,int eventId);
	public boolean deleteEvent(int eventId);
}