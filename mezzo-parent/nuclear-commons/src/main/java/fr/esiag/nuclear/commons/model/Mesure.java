package fr.esiag.nuclear.commons.model;

import java.io.Serializable;

public abstract class Mesure implements Serializable{

	private String idCapteur;
	private long value;
	private String unite;
		
	public abstract Object mesureCapte(String idCapteur,long valeur,String unite);

	public String getIdCapteur() {
		return idCapteur;
	}

	public void setIdCapteur(String idCapteur) {
		this.idCapteur = idCapteur;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}
	
	
}
