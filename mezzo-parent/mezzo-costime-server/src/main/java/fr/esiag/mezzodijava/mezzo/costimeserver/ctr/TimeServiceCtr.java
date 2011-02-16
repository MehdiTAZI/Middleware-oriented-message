package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

public class TimeServiceCtr {

	private TimeServiceModel model;
	
	public void subscribe(Synchronizable cc) throws AlreadyRegisteredException,
	UnreachableException {
		
	}
	public void unsubscribe(Synchronizable cc) throws UnreachableException,
	NotRegisteredException {

	} 

}
