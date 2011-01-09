package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Vector;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;

public class EventServer {
	/*
	 * Auteur : MTA
	 */
	private Vector<ChannelCtr> channels;
	
	public EventServer()
	{
		
	}
	public EventServer(Vector<ChannelCtr> channels) {
		super();
		this.channels = channels;
	}
	public Vector<ChannelCtr> getChannels() {
		return channels;
	}

	public void setChannels(Vector<ChannelCtr> channels) {
		this.channels = channels;
	}
	
	public void addChannel(ChannelCtr ChannelCtr)
	{
		this.channels.add(ChannelCtr);
	}
	
	public void removeChannel(ChannelCtr ChannelCtr)
	{
		this.channels.removeElement(ChannelCtr);
	}
	
	public static Channel getChannelModel(ChannelCtr channelCtr)
	{	
		return channelCtr.getChannelModel();
	}
}
