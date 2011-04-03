package fr.esiag.mezzodijava.mezzo.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.esiag.mezzodijava.mezzo.libclient.ManagerClient;
import fr.esiag.mezzodijava.mezzo.manager.client.AuthenticationService;
import fr.esiag.mezzodijava.mezzo.manager.shared.ChannelInfosCollector;
import fr.esiag.mezzodijava.mezzo.manager.shared.Message;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollector;

public class AuthenticationServiceImpl  extends RemoteServiceServlet implements AuthenticationService {
	private static final long serialVersionUID = 1L;

	public ChannelInfosCollector[] authenticate(String loging, String pwd) {
		System.out.println(loging+":"+pwd);
		ManagerClient manager=ManagerClient.init(new String[]{"-ORBInitRef NameService=corbaloc::127.0.0.1:1050/NameService",
				"-Djacorb.home=C:\\mezzodev\\jacorb-2.3.1",
				"-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB",
				"-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"});	
		CosInfoCollector managerInfo=manager.resolveManagerService("CosInfoCollector");
		fr.esiag.mezzodijava.mezzo.monitoring.ChannelInfosCollector[] channelInfosCollector=new fr.esiag.mezzodijava.mezzo.monitoring.ChannelInfosCollector[managerInfo.getChannelsInfos().length];
		channelInfosCollector=managerInfo.getChannelsInfos();
		ChannelInfosCollector[] channelsInfo=new ChannelInfosCollector[channelInfosCollector.length];
		int i=0;
		
		for(fr.esiag.mezzodijava.mezzo.monitoring.ChannelInfosCollector channelInfo:channelInfosCollector){
			channelsInfo[i].capacity=channelInfo.capacity;
			channelsInfo[i].consumersConnected=channelInfo.consumersConnected;
			channelsInfo[i].consumersSubscribed=channelInfo.consumersSubscribed;
			channelsInfo[i].messages=new Message[channelInfo.messages.length];
			int j=0;
			for(fr.esiag.mezzodijava.mezzo.monitoring.Message message:channelInfo.messages){
				channelsInfo[i].messages[j].code=message.code;
				channelsInfo[i].messages[j].data=message.data;
				channelsInfo[i].messages[j].time=message.time;
				channelsInfo[i].messages[j].type=message.type;
				j++;
			}
			channelsInfo[i].nbQueueEvents=channelInfo.nbQueueEvents;
			channelsInfo[i].suppliersConnected=channelInfo.suppliersConnected;
			channelsInfo[i].topic=channelInfo.topic;
			i++;
		}
		return channelsInfo;
	}
}