package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.ArrayList;
import java.util.List;

public class EventServer {
	/*
	 * Auteur : MTA
	 */
	private List<Channel> channels = new ArrayList<Channel>();

	public EventServer() {

	}

	public EventServer(List<Channel> channels) {
		super();
		this.channels = channels;
	}

	public void addChannel(Channel channel) {
		this.channels.add(channel);
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void removeChannel(Channel channel) {
		this.channels.remove(channel);
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
}
