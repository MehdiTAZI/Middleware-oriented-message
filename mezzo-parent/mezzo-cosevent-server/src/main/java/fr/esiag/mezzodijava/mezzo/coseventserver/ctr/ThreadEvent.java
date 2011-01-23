package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Iterator;
import java.util.List;

import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class ThreadEvent implements Runnable {
	private Channel channel;

	public ThreadEvent(String topic) {
		channel = BFFactory.getChannel(topic);
	}

	@Override
	public void run() {
		while (true) {
			// For all Subscribed Consumer to the Channel
			for (ProxyForPushConsumerImpl consumer : channel
					.getConsumersSubscribed().keySet()) {
				// if the consumer is connected

				if (channel.getConsumersConnected().contains(consumer)) {
					// for all events of the consumer
					List<Event> le = channel.getConsumersSubscribed().get(
							consumer);
					synchronized (le) {
						Iterator<Event> i = le.iterator(); // Must be in
															// synchronized
															// block
						while (i.hasNext()) {
							Event e = i.next();
							// for (Event e : le) {
							try {
								// TODO : here manage life of the events.
								// send event to the consumer
								consumer.receive(e);
								// remove event from the list
								i.remove();

							} catch (ConsumerNotFoundException e1) {
								// TODO log here
								e1.printStackTrace();
								// Consumer seems to be unreachable so it's time
								// to disconnect it
								try {
									consumer.disconnect();
								} catch (NotConnectedException e2) {
									e2.printStackTrace();
								} catch (NotRegisteredException e2) {
									e2.printStackTrace();
								}

								// TMA : todo => ajouter les messages non recu
								// de la Queuqe
								// Sans oublier d'ajoutre dans la class qu'il
								// faut le faite d'essyez
								// denvoyer lensemble des messages lors de la
								// connexion du consumer
							}
						}
					}
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
