package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

public class TimeServiceCtr {

	private TimeServiceModel model;
	public TimeServiceModel getModel() {
		return model;
	}
	public TimeServiceCtr(TimeServiceModel model){
		this.model=model;
	}
	public void subscribe(Synchronizable cc) throws AlreadyRegisteredException,
	UnreachableException {
		if(!model.getComponentSubscribed().add(cc))
			throw new AlreadyRegisteredException();
	}
	public void unsubscribe(Synchronizable cc) throws UnreachableException,
	NotRegisteredException {
		if(!model.getComponentSubscribed().contains(cc))
			throw new NotRegisteredException();
		model.getComponentSubscribed().remove(cc);
	} 
}
