package fr.esiag.mezzodijava.mezzo.costimeserver.model;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;

public class TimeServiceModel {

	private List<Synchronizable> componentSubscribed = Collections
    .synchronizedList((new Vector <Synchronizable>()));
	
	public List<Synchronizable> getComponentSubscribed() {
		return componentSubscribed;
	}
	
}
