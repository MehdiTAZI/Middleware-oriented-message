package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.derby.iapi.services.io.NewByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
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
    private Map<Integer,EventModel> listEvent=Collections.synchronizedMap(new HashMap<Integer, EventModel>());
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
                for(ConsumerModel consumerModel:channel.getConsumers().values()){
                    for(EventModel event:consumerModel.getEventsInQueue()){
                        if(!listEvent.containsKey(event.getId())){
                            listEvent.put(event.getId(),event);
                        }
                    }
                }
                for(ConsumerModel consumerModel:channel.getConsumersPull().values()){
                    for(EventModel event:consumerModel.getEventsInQueue()){
                        if(!listEvent.containsKey(event.getId())){
                            listEvent.put(event.getId(),event);
                        }
                    }
                }
                
                list[i].messages=new Message[listEvent.size()];
                list[i].messages=EventsToMessage();
                listEvent=new HashMap<Integer, EventModel>();
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
    private Message[] EventsToMessage(){
        log.debug("Access to EventtoMessage Module");
        Message [] messages=new Message[listEvent.size()];
        int i=0;
        for(EventModel event:listEvent.values())
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
            i++;
        }
        return messages;
    }

}