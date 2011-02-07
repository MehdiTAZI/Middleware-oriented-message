package fr.esiag.mezzodijava.nuclear.systemstatemonitor;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

public class MainMonitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			InjectorSystemStateSupplier supplier;
			try {
				supplier = new InjectorSystemStateSupplier();
				System.out.println("creation supplier injector");
				NuclearSensorConsumer consumer = new NuclearSensorConsumer(supplier);
				System.out.println("creation consumer nuclear");
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
