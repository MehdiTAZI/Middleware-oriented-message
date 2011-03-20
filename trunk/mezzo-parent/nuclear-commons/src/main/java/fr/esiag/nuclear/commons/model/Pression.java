package fr.esiag.nuclear.commons.model;

import java.io.Serializable;

public class Pression extends Mesure implements Serializable{

	public Pression() {
		// TODO Auto-generated constructor stub
		setValue(0);
		setUnite("Pascal");
	}
	
	
	public Pression(long value, String unite) {
		// TODO Auto-generated constructor stub
		
		setValue(value);
		setUnite(unite);
	}
	
	@Override
	public Pression mesureCapte(String idCapteur, long valeur, String unite) {
		setIdCapteur(idCapteur);
		return new Pression(valeur, unite);
		
	}

}
