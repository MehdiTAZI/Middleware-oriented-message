package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

/**
 * Interface JdbcDOA.
 * 
 * Data Acces Object to persist cosevent domain classes in a database. Provide
 * method to save and load object like Channel, Consumer and EventModel.
 * 
 * UC n°: US41 (+US children) CI 06: Persistance des messages.
 * 
 * @see JdbcDAO
 * @author Mezzo-Team
 */
public interface JdbcDAO {

    /* methodes de selection */
    /**
     * access to all the channel
     * 
     * @return a list of the channel
     */
    public List<Channel> findAllChannel();

    /**
     * access to all the event for a channel
     * 
     * @param channelId
     *            the channel
     * @return all the event associated with channelId
     */
    public SortedSet<EventModel> findEventByChannel(int channelId);

    /**
     * sort all the consumer for one channel
     * 
     * @param channelId
     *            the channel
     * @return Map with the IDConsumer and the consumer associated
     */
    public Map<String, ConsumerModel> findConsumerByChannel(int channelId);

    /**
     * @return event for the consumer associated
     */
    public SortedSet<EventModel> findEventByConsumer(int idConsumer);

    /* methodes d'insertion */

    /**
     * @return the channelId associated to the channel that we wanted to persist
     */
    public int insertChannel(Channel channel);

    /**
     * @return return the consumerID associated to the consumerModel that we
     *         wanted to persist
     */
    public int insertConsumer(ConsumerModel consumer);

    /**
     * @return the eventId associated to the eventModel and the channelId.
     */
    public int insertEvent(int channelId, EventModel event);

    /**
     * add the Event to the consumer
     */
    public void addEventToConsumer(int eventId, int consumerId);

    /* methodes de mise à jour */
    /**
     * refresh capacity and id of channel
     */
    public void updateChannel(Channel channel);

    /* methodes de suppression */
    /**
     * delete an event associated to a consumer
     */
    public void deleteEventByConsumer(int consumerId, int eventId);

    /**
     * delete an event
     */
    public void deleteEvent(int eventId);

    /**
     * delete a consumer who was already persisted
     */
    public void deleteConsumer(int consumerId);

    /**
     * delete a channel who was already persisted
     */
    public void deleteChannel(int channelId);

    /**
     * delete all consumers associated to a channel
     */
    public void deleteAllConsumers(int channelId);
}
