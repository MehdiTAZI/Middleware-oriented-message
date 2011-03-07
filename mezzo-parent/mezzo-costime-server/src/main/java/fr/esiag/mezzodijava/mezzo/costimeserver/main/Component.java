package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import java.util.Date;

import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;

/**
 * Component : it s a component wich wants to synchronize its date and time
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 * 
 */

public class Component implements SynchronizableOperations{
	private Date date;
	
	/**
	 * Get the date as long
	 *
	 * @return time
	 *
	 * @see fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations#date()
	 */
	@Override
	public long date() {
		return date.getTime();
	}
 
	/**
	 * Set the date
	 *
	 * @param date
	 * 			date as long
	 * @see fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations#date(long date)
	 *
	 */
	@Override
	public void date(long date) {
		
		this.date=new Date(date);
		System.out.println("La date synchronisé "+ date);
	}

}
