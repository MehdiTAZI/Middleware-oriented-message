package fr.esiag.mezzodijava.mezzo.coseventserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRAPEventServer {
	private String name;
	private Map<String,CRAPChannelctr> channels;
	
	public CRAPEventServer(String name){
		this.name=name;
		channels = new HashMap<String,CRAPChannelctr>();
	}
	public void addChannel(CRAPChannelctr channel){
		channels.put(channel.getTopic(), channel);
	}
	public void removeChannel(String topic){
		channels.remove(topic);
	}

}
