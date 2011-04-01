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

    /**
     * Get the unique identifier with a random
     * 
     * @return id
     * 
     */
    public static long getUniqueIdentifier() {
	return (long) (Math.random() * 1000) + 10;
    }

}
