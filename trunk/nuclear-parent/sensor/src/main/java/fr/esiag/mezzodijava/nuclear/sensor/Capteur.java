package fr.esiag.mezzodijava.nuclear.sensor;

import java.util.Vector;

public class Capteur {
	private Vector<String> data;
	private Implementor imp;
	
	public Capteur(Implementor imp) {
		this.imp=imp;
		//data=imp.getData();		
	}
	
	public Vector<String> getData(String file){
		return imp.getData(file);
	}
	
	
}
