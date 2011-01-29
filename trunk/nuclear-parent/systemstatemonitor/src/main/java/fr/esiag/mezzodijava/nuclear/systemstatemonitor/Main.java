package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			InjectorSystemStateSupplier supplier;
			try {
				supplier = new InjectorSystemStateSupplier();
				NuclearSensorConsumer consumer = new NuclearSensorConsumer(supplier);
				
			} catch (ChannelNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyRegisteredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EventClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TopicNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

}
