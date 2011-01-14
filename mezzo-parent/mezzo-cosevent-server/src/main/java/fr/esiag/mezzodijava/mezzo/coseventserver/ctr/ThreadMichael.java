package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;

public class ThreadMichael implements Runnable{
	private ChannelCtr channelCtr;
	public ThreadMichael(String channel){
		BFFactory.createChannelCtr(channel);
	}

	@Override
	public void run() {
		while(true){
			for(ProxyForPushConsumerImpl consumer:channelCtr.getChannel().getConsumersSubscribed().keySet()){
				if(channelCtr.getChannel().getConsumersConnected().contains(consumer))
					for(Event e:channelCtr.getChannel().getConsumersSubscribed().get(consumer))
						try {
							consumer.receive(e);
						} catch (ConsumerNotFoundException e1) {
							e1.printStackTrace();
						}
			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
