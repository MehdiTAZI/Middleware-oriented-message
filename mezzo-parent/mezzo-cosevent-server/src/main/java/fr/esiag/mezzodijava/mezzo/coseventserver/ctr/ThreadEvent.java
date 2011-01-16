package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.ArrayList;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;

public class ThreadEvent implements Runnable{
	private ChannelCtr channelCtr;
	public ThreadEvent(String channel){
		channelCtr = BFFactory.createChannelCtr(channel);
	}

	@Override
	public void run() {
		while(true){
			for(ProxyForPushConsumerImpl consumer:channelCtr.getChannel().getConsumersSubscribed().keySet()){
				if(channelCtr.getChannel().getConsumersConnected().contains(consumer))
					for(Event e:channelCtr.getChannel().getConsumersSubscribed().get(consumer))
						try {
							consumer.receive(e);
							//TMA : todo => supprimer les messages recu de la Queuqe  
							
						} catch (ConsumerNotFoundException e1) {
							e1.printStackTrace();
							//TMA : todo => ajouter les messages non recu de la Queuqe
							//Sans oublier d'ajoutre dans la class qu'il faut le faite d'essyez
							//denvoyer lensemble des messages lors de la connexion du consumer
						}
					//TMA : je ne suis pas dacorrd
					channelCtr.getChannel().getConsumersSubscribed().put(consumer, new ArrayList<Event>());

			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
