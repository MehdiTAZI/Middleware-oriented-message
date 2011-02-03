package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

public class RandomChannelIdentifier {

	
	private static long id;
	
	private RandomChannelIdentifier() {
		id=1000;
	}

	public static long getUniqueIdentifier() {
		return id=(long)(Math.random()*1000)+10;
	}

	
}
