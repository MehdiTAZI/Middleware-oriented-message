package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;
import fr.esiag.mezzodijava.mezzo.monitoring.ChannelInfosCollector;
import fr.esiag.mezzodijava.mezzo.monitoring.CosInfoCollectorOperations;
import fr.esiag.mezzodijava.mezzo.monitoring.Message;

/**
 * 
 * @author MEZZODIJAVA
 * Provide informations
 *
 */
public class CosInfoCollectorImpl implements CosInfoCollectorOperations{

	private static Logger log = LoggerFactory.getLogger(CosInfoCollectorImpl.class);
	/**
	 * @return an array containing the channel infos
	 */
	@Override
	public ChannelInfosCollector[] getChannelsInfos() {
		log.debug("Access to channels infos");
		EventServer es = EventServer.getInstance();
		ChannelInfosCollector list[] = new ChannelInfosCollector[es.getMapChannel().size()];
		log.trace("Size of the MapChannel is "+es.getMapChannel().size());
		if(es.getMapChannel().size()>0)
		{
			int i=0;
			for(Channel channel:es.getMapChannel().values())
			{
				list[i]=new ChannelInfosCollector();
				list[i].topic=channel.getTopic();
				list[i].capacity=channel.getCapacity();
				list[i].consumersConnected=channel.getConsumersConnected().size();
				list[i].consumersSubscribed=channel.getConsumers().size();
				list[i].suppliersConnected=channel.getSuppliersConnected().size();
				list[i].nbQueueEvents=0;//channel.getEvents().size();
				list[i].messages=new Message[0];//EventsToMessage(channel.getEvents());
				log.trace("Infos on channel :"+ list[i].topic+". ");
				log.trace("Capacity = "+ list[i].capacity+". ");
				log.trace("NbQueueEvents = "+ list[i].nbQueueEvents+". ");
				log.trace("ConsumersConnected = "+ list[i].consumersConnected+". ");
				log.trace("ConsumersSubscribed = "+ list[i].consumersSubscribed+". ");
				log.trace("SuppliersConnected = "+ list[i].suppliersConnected+". ");
				log.trace("Messages (nb)= "+ list[i].messages.length+". ");
				log.trace("End of infos on channel :"+ list[i].topic+". ");
				i++;
			}
		}
		return list;
	}
	private Message[] EventsToMessage(SortedSet<EventModel> events){
		log.debug("Access to EventtoMessage Module");
		Message [] messages=new Message[events.size()];
		int i=0;
		for(EventModel event:events)
		{
			messages[i]=new Message();
			messages[i].code=""+event.getCode();
			messages[i].time=""+event.getTimetolive();
			messages[i].type=""+event.getType();
			messages[i].data=new String(event.getData());
			log.trace("Message number {}",i);
			log.trace("Code: "+messages[i].code);
			log.trace("Time: "+messages[i].time);
			log.trace("Type: "+messages[i].type);
			log.trace("Data: "+messages[i].data);
			log.trace("End of message number {}",i);
		}
		return messages;
	}

}
