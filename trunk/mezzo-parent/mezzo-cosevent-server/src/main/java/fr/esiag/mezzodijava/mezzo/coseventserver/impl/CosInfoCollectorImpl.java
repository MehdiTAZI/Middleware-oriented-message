package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.SortedSet;

import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.monitoring.ChannelInfosCollector;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollectorOperations;
import fr.esiag.mezzodijava.mezzo.monitoring.Message;

public class CosInfoCollectorImpl implements CosInfoCollectorOperations{

	@Override
	public ChannelInfosCollector[] getChannelsInfos() {
		EventServer es = EventServer.getInstance();
		ChannelInfosCollector list[] = new ChannelInfosCollector[es.getMapChannel().size()];
		if(es.getMapChannel().size()>0)
		{
			int i=0;
			for(Channel channel:es.getMapChannel().values())
			{
				list[i].topic=channel.getTopic();
				list[i].capacity=channel.getCapacity();
				list[i].consumersConnected=channel.getConsumersConnected().size();
				list[i].consumersSubscribed=channel.getConsumers().size();
				list[i].suppliersConnected=channel.getSuppliersConnected().size();
				list[i].nbQueueEvents=channel.getEvents().size();
				list[i].messages=EventsToMessage(channel.getEvents());
				i++;
			}
		}
		return list;
	}
	private Message[] EventsToMessage(SortedSet<EventModel> events){
		Message [] messages=new Message[events.size()];
		int i=0;
		for(EventModel event:events)
		{
			messages[i].code=""+event.getCode();
			messages[i].time=""+event.getTimetolive();
			messages[i].type=""+event.getType();
			messages[i].data=new String(event.getData());
		}
		return messages;
	}

}
