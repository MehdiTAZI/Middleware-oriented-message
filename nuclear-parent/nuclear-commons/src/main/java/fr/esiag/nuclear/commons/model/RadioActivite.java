package fr.esiag.nuclear.commons.model;

import java.io.Serializable;

public class RadioActivite extends Mesure implements Serializable{
	
	
	public RadioActivite() {
		setValue(0);
		setUnite("SIEVERT");
	}
	
	
	public RadioActivite(long value,String unite) {
		setValue(value);
		setUnite(unite);
	}
	
	@Override
	public RadioActivite mesureCapte(String idCapteur, long valeur, String unite) {
		
		setIdCapteur(idCapteur);
		return new RadioActivite(valeur,unite);
		
	}

}
