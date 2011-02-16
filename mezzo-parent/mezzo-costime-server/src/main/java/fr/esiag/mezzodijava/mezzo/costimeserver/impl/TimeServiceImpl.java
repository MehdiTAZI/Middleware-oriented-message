package fr.esiag.mezzodijava.mezzo.costimeserver.impl;

import java.util.Date;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.TimeServiceOperations;
import fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;

public class TimeServiceImpl implements TimeServiceOperations{

	private TimeServiceCtr ctr;
	@Override
	public void subscribe(Synchronizable cc) throws AlreadyRegisteredException,
			UnreachableException {
		
	}
	@Override
	public void unsubscribe(Synchronizable cc) throws UnreachableException,
			NotRegisteredException {
				
	}

}
