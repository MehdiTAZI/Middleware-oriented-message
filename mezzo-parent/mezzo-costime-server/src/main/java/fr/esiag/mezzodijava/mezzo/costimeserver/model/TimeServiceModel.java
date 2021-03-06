package fr.esiag.mezzodijava.mezzo.costimeserver.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static Logger log = LoggerFactory.getLogger(TimeServiceModel.class);
	
	private Set<Synchronizable> componentSubscribed = Collections
    .synchronizedSet((new HashSet<Synchronizable>()));
	
	/**
	 * Get the list of subscribed components
	 *
	 * @return List<Synchronizable>
	 * 				list of subscribed components
	 *
	 */
	public Set<Synchronizable> getComponentSubscribed() {
		return componentSubscribed;
	}	 
}
