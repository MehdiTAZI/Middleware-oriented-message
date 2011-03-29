package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity Class EventServer
 * 
 * For storing server level datas and Channels list.
 * 
 * UC n°07: US10
 * 
 * @author Mezzo-Team
 * 
 */
public class EventServer {

    private static EventServer instance = null;

    private Map<String, Channel> mapChannel = new HashMap<String, Channel>();

    private Map<Long, Channel> mapChannelId = new HashMap<Long, Channel>();
    
    /**
     * Constructor.
     */
    private EventServer() {

    }

    /**
     * Return the singleton instance of the EventServer entity.
     * 
     * @return singleton of EventServer entity
     */
    public static synchronized EventServer getInstance() {
	if (instance == null) {
	    instance = new EventServer();
	}
	return instance;
    }

    /**
     * Create a Channel Entity associated with the given topic.
     * 
     * @param topic
     *            Channel topic
     * @param capacity
     *            Maximal connection allowed
     * @return existing or new Channel for this topic.
     */
    public synchronized Channel createChannelEntity(String topic,
	    int capacity) {
	if (mapChannel.get(topic) == null) {
	    Channel channel = new Channel(topic, capacity);
	    mapChannel.put(topic, channel);
		// Register the channel entity in id map
	    mapChannelId.put(channel.getIdentifier(), channel);
	    System.out.println("createChannelEntoty " + channel.getIdentifier());
	}
	return mapChannel.get(topic);
    }
    
    /**
     * Add a Channel.
     * 
     * @param channel
     *            Channel to add.
     */
    public void addChannel(Channel channel) {
	this.mapChannel.put(channel.getTopic(),channel);
	this.mapChannelId.put(channel.getIdentifier(),channel);
    }
    
    /**
     * Return current instance of Channel Bean associated with this topic or
     * <code>null</null> if not channel exists.
     * 
     * @param topic
     *            The Topic of the wanted channel.
     * @return Channel with the specified topic or <code>null</null>
     */
    public Channel getChannel(String topic) {
	return mapChannel.get(topic);
    }

    /**
     * Return current instance of Channel Bean associated with this unique id or
     * <code>null</null> if id not exists.
     * 
     * @param id
     *            The unique id of the wanted channel.
     * @return Channel with the specified id or <code>null</null>
     */
    public Channel getChannel(long id) {
	return mapChannelId.get(id);
    }

    /**
     * Test purpose only. Enable to inject a mock object in the ChannelFactory.
     * 
     * @param topic
     * @param alternateChannel
     *            An alternative implementation of Channel typically a mock
     */
    public synchronized void setAlternateChannel(String topic,
	    Channel channel) {
	mapChannel.put(topic, channel);
    }
    
    /**
     * Remove a channel.
     * 
     * @param channel
     *            Channel to remove
     */
    public void removeChannel(Channel channel) {
	this.mapChannel.remove(channel.getTopic());
	this.mapChannelId.remove(channel.getIdentifier());
    }

    public Map<String, Channel> getMapChannel() {
        return mapChannel;
    }

    public void setMapChannel(Map<String, Channel> mapChannel) {
        this.mapChannel = mapChannel;
    }

    public Map<Long, Channel> getMapChannelId() {
        return mapChannelId;
    }

    public void setMapChannelId(Map<Long, Channel> mapChannelId) {
        this.mapChannelId = mapChannelId;
    }
}
