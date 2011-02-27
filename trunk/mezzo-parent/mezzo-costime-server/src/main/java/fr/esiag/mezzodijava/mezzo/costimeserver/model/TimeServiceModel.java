package fr.esiag.mezzodijava.mezzo.costimeserver.model;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;

/**
 * TimeServiceModel : contain subscribed list of component for time service
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 * 
 */
public class TimeServiceModel {

	private List<Synchronizable> componentSubscribed = Collections
    .synchronizedList((new Vector <Synchronizable>()));
	
	/**
	 * Get the list of subscribed components
	 *
	 * @return List<Synchronizable>
	 * 				list of subscribed components
	 *
	 */
	public List<Synchronizable> getComponentSubscribed() {
		return componentSubscribed;
	}	
}
