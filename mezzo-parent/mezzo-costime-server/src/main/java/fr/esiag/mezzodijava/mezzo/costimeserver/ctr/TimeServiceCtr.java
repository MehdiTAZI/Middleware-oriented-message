package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import java.util.Collections;
import java.util.TreeSet;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.UnreachableException;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

/**
 * TimeServiceCtr : To interact with the model
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 * 
 */

public class TimeServiceCtr {

	private TimeServiceModel model;
	
	/**
	 * Get the model
	 *
	 * @return TimeServiceModel
	 *
	 */
	public TimeServiceModel getModel() {
		return model;
	}
	
	/**
	 * Constructor
	 *
	 * @param model
	 *            the TimeServiceModel
	 */
	public TimeServiceCtr(TimeServiceModel model){
		this.model=model;
	}
	
	/**
     * Subscribe a component to the channel.
     * 
     * The server will push the time to this component.
     * 
     * @param component
     * 			a component of type Synchronizable
     * @throws AlreadyRegisteredException
     *             If already present in the list.
     */
	public void subscribe(Synchronizable cc) throws AlreadyRegisteredException{

		if(!model.getComponentSubscribed().add(cc))
			throw new AlreadyRegisteredException();
		System.out.println("Component subscribed : " + cc);
		
			/*FOR VECTOR VERSION
			 * if (model.getComponentSubscribed().contains(cc)
					|| cc == null) {
				System.out.println("Component allready subscribed : " + cc);
				throw new AlreadyRegisteredException("Component allready subscribed ");
				
			} else {
				model.getComponentSubscribed().add(cc);
				System.out.println("Component subscribed : " + cc);
			}*/
	}
	
	/**
     * Unsubscribe a component from the channel.
     * 
     * remove the component from the list 
     * 
     * @param component
     * 			a component of type Synchronizable
     * @throws NotRegisteredException
     *             If the component is not registered.
     */
	public void unsubscribe(Synchronizable cc) throws NotRegisteredException {
		if(!model.getComponentSubscribed().contains(cc))
			throw new NotRegisteredException();
		model.getComponentSubscribed().remove(cc);
		System.out.println("Component unsubscribed : " + cc);
	} 
}
