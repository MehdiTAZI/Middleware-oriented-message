package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

/**
 * Classe RandomChannelIdentifier
 * 
 * To make the Unique Identifier of a channel
 * 
 * UC n°07: US10 (+US children)
 * 
 * @author Mezzo-Team
 * 
 */
public class RandomChannelIdentifier {

	
	private static long id;
	
	/**
     * Constructor for a RandomChannelIdentifier 
     *
     */
	private RandomChannelIdentifier() {
		id=1000;
	}

	/**
	 * Get the unique identifier with a random
	 *
	 * @return id
	 *
	 */
	public static long getUniqueIdentifier() {
		return id=(long)(Math.random()*1000)+10;
	}

	
}
