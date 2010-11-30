package fr.esiag.mezzodijava.mezzoproto.capteur;

import java.util.Vector;

public class Capteur {
	private Vector<String> data;
	
	public Capteur(Implementor imp) {
		data=imp.getData();		
	}
	
	public Vector<String> getData(){
		return data;
	}
	
	
}
