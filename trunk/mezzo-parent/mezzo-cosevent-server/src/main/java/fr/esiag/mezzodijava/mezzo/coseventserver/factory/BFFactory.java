package fr.esiag.mezzodijava.mezzo.coseventserver.factory;





import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.exceptions.EventServerException;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class BFFactory {

	private static ORB orb;
	private static HashMap<String, Channel> mapChannel = new HashMap<String, Channel>();
	private static HashMap<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();
	private static HashMap<String, ChannelAdminCtr> mapChannelAdminCtr = new HashMap<String, ChannelAdminCtr>();
	private static HashMap<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();
	
	/*TMA : tout les methode static ici doivent etre synchronized 
	en cas d'appel concurent ( remarque de Mr.Gill Giraud ) */
	
	public synchronized static ORB createOrb(String[] args, Properties props){
		System.out.println(orb);
		if(orb==null)
			orb = ORB.init(args, props);
		return orb;
		
	}

	public synchronized static Channel createChannel(String channel, int capacity){
		if (mapChannel.get(channel)==null)
			mapChannel.put(channel, new Channel(channel, capacity));
		return mapChannel.get(channel);
	}
	
	public synchronized static ChannelCtr createChannelCtr (String channel){
		if (mapChannelCtr.get(channel)==null)
			mapChannelCtr.put(channel, new ChannelCtr(channel));
		return mapChannelCtr.get(channel);
		
	}
	
	public synchronized static ChannelAdminCtr createChannelAdminCtr(String channel){
		if (mapChannelAdminCtr.get(channel)==null)
			mapChannelAdminCtr.put(channel, new ChannelAdminCtr(channel));
		return mapChannelAdminCtr.get(channel);
		
	}
	
	public synchronized static ChannelAdminImpl createChannelAdminImpl(String channel){
		if (mapChannelAdminImpl.get(channel)==null)
			mapChannelAdminImpl.put(channel, new ChannelAdminImpl(channel));
		return mapChannelAdminImpl.get(channel);
	}
	
	public synchronized static ChannelAdminImpl initiateChannel(String channel,int capacity){
		createChannel(channel, capacity);
		return createChannelAdminImpl( channel);
	}
	

	
	
}
