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
	
	public void updateChannel (Channel channel);
	
	/* methodes de suppression */
	
	public void deleteEventByConsumer(int consumerId,int eventId);
	public void deleteEvent(int eventId);
	public void deleteConsumer(int consumerId);
	public void deleteChannel(int channelId);
	public void deleteAllConsumers();
}
