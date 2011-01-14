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
	
	public static ORB createOrb(String[] args, Properties props){
		System.out.println(orb);
		if(orb==null)
			orb = ORB.init(args, props);
		return orb;
		
	}
	public static Channel createChannel(String channel){
		if (mapChannel.get(channel)==null)
			mapChannel.put(channel, new Channel(channel));
		return mapChannel.get(channel);
	}
	
	public static ChannelCtr createChannelCtr (String channel){
		if (mapChannelCtr.get(channel)==null)
			mapChannelCtr.put(channel, new ChannelCtr(channel));
		return mapChannelCtr.get(channel);
		
	}
	
	public static ChannelAdminCtr createChannelAdminCtr(String channel){
		if (mapChannelAdminCtr.get(channel)==null)
			mapChannelAdminCtr.put(channel, new ChannelAdminCtr(channel));
		return mapChannelAdminCtr.get(channel);
		
	}
	
	public static ChannelAdminImpl createChannelAdminImpl(String channel){
		if (mapChannelAdminImpl.get(channel)==null)
			mapChannelAdminImpl.put(channel, new ChannelAdminImpl(channel));
		return mapChannelAdminImpl.get(channel);
	}
	
	public static ChannelAdminImpl initiateChannel(String channel){
		return createChannelAdminImpl( channel);
	}
	

	
	
}
