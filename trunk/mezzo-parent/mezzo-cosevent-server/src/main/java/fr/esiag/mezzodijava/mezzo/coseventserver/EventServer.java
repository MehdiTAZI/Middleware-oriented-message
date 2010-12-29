package fr.esiag.mezzodijava.mezzo.coseventserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventServer {
	private String name;
	private Map<String,Channelctr> channels;
	
	public EventServer(String name){
		this.name=name;
		channels = new HashMap<String,Channelctr>();
	}
	public void addChannel(Channelctr channel){
		channels.put(channel.getTopic(), channel);
	}
	public void removeChannel(String topic){
		channels.remove(topic);
	}

}
