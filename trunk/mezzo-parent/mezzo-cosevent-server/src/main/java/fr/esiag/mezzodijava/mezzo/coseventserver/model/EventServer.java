package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity Class EventServer
 * 
 * For storign server level datas and Channels list.
 * 
 * UC n°: US10
 * 
 * @author Mezzo-Team
 * 
 */
public class EventServer {
    /*
     * Auteur : MTA
     */
    private List<Channel> channels = new ArrayList<Channel>();

    /**
     * Constructor.
     */
    public EventServer() {

    }

    /**
     * Add a Channel.
     * 
     * @param channel
     *            Channel to add.
     */
    public void addChannel(Channel channel) {
	this.channels.add(channel);
    }

    /**
     * Get list of Channels.
     * 
     * @return List of Channels?
     */
    public List<Channel> getChannels() {
	return channels;
    }

    /**
     * Remove a channel.
     * 
     * @param channel
     *            Channel to remove
     */
    public void removeChannel(Channel channel) {
	this.channels.remove(channel);
    }

    /**
     * Set the Channel List.
     * 
     * @param channels
     *            List of Channels
     */
    public void setChannels(List<Channel> channels) {
	this.channels = channels;
    }
}
