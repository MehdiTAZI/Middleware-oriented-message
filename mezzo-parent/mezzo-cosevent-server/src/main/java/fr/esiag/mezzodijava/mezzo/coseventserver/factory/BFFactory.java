package fr.esiag.mezzodijava.mezzo.coseventserver.factory;





import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class BFFactory {

	private static HashMap<String, Channel> mapChannel = new HashMap<String, Channel>();
	private static HashMap<String, ChannelCtr> mapChannelCtr = new HashMap<String, ChannelCtr>();
	private static HashMap<ORB, HashMap<String, ChannelAdminCtr>> mapChannelAdminCtrOrb = new HashMap<ORB,  HashMap<String, ChannelAdminCtr>>();
	private static HashMap<String, ChannelAdminImpl> mapChannelAdminImpl = new HashMap<String, ChannelAdminImpl>();
	
	public static Channel getChannel(String channel){
		if (mapChannel.get(channel)==null)
			mapChannel.put(channel, new Channel(channel));
		return mapChannel.get(channel);
	}
	
	public static ChannelCtr getChannelCtr (String channel){
		if (mapChannelCtr.get(channel)==null)
			mapChannelCtr.put(channel, new ChannelCtr(getChannel(channel)));
		return mapChannelCtr.get(channel);
		
	}
	
	public static ChannelAdminCtr getChannelAdminCtr(ORB orb,String channel){
		if (mapChannelAdminCtrOrb.get(orb)==null)
			mapChannelAdminCtrOrb.put(orb, new HashMap<String, ChannelAdminCtr>());
		if (mapChannelAdminCtrOrb.get(orb).get(channel)==null)
			mapChannelAdminCtrOrb.get(orb).put(channel, new ChannelAdminCtr(orb, getChannelCtr(channel)));
		return mapChannelAdminCtrOrb.get(orb).get(channel);
		
	}
	
	public static ChannelAdminImpl getChannelAdminImpl(ORB orb,String channel){
		if (mapChannelAdminImpl.get(channel)==null)
			mapChannelAdminImpl.put(channel, new ChannelAdminImpl(getChannelAdminCtr(orb, channel)));
		return mapChannelAdminImpl.get(channel);
	}
	
	public static ChannelAdminImpl createChannel(ORB orb,String channel){
		return getChannelAdminImpl(orb, channel);
	}
	

	
	
}
